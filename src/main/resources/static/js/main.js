let content1 = document.querySelector('#info iframe');
let content2 = document.querySelector('#subwayInfo');
let mapOverlay;
let hover = null;
let i = 0;

document.cookie = "safeCookie1=foo; SameSite=Lax"; 
document.cookie = "safeCookie2=foo"; 
document.cookie = "crossCookie=bar; SameSite=None; Secure";

const sources = new ol.source.OSM();

const baseLayer = new ol.layer.Tile({
  source: new ol.source.OSM(),
  id : "baseLayer",
  visible : true,
  type : 'base'
});

const vworldLayer = 
  new ol.layer.Tile({
    title : 'Vworld Map',
		visible : true, 
		type : 'base', 
		source : new ol.source.XYZ({ 
				url : 'http://api.vworld.kr/req/wmts/1.0.0/EB759A77-B32A-3AEE-A33A-4272DB483DFA/Base/{z}/{y}/{x}.png'
			})
  });   

const sigLayer =  new ol.layer.Tile({
    opacity: 0.5,
    source: new ol.source.TileWMS({
      url: "http://localhost:8081/geoserver/FirstProject/wms?service=WMS",
      params: {
        VERSION: "1.1.0",
        LAYERS: "FirstProject:sido",
        BBOX: [
          742901.0625,1455705.5,1391157.0,2071492.5
        ],
        SRS: "EPSG:5179",
      },
      serverType: "geoserver",
    }),
  });

const view = new ol.View({
  center: ol.proj.fromLonLat([129, 35.5]),
  zoom: 6,
  minZoom: 6,
});

const map = new ol.Map({
  layers: [vworldLayer, sigLayer],
  target: "map",
  view: view,
});

let reg = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/   ]/gim;

function doCORSRequestSubway(result){ 
  let encoded = encodeURI(result);
  let res2;
  let subwayName = [];
  let subway_nm = [];
  let url = 'http://localhost:8081/geoserver/FirstProject/wms?SERVICE=WMS'
  +'&VERSION=1.1.1&REQUEST=GetFeatureInfo&FORMAT=image%2Fpng&TRANSPARENT=true'
  +'&QUERY_LAYERS=FirstProject%3Asubway_korea&STYLES&LAYERS=FirstProject%3Asubway_korea'
  +'&exceptions=application%2Fvnd.ogc.se_inimage&INFO_FORMAT=text%2Fhtml&FEATURE_COUNT=1200&X=50&Y=50&SRS=EPSG%3A4326&WIDTH=101&HEIGHT=101&BBOX=57.65625%2C-33.75%2C199.6875%2C108.28125'
  +`&CQL_FILTER=SBW_STATN_ADDR%20LIKE%20%27%25${result}%25%27`
  // content2.src = url;
  $.ajax({
    method: 'GET',
    url: url,
    async: false,
    success: function(result) {
      let data = result.replaceAll('td', '')
      let k = 0;   
      res2 = data.replaceAll(reg, '').split('\n')
      for (let i = 0; i < res2.length; i++) {
        if(i == 49){
          subway_nm.push(res2[i])
        }
        if(i > 49){
          k++;
          if(k % 10 == 0 && res2[i] != 'html'){           
            subway_nm.push(res2[i])
          }
        }
      }
      res = result  
      const set = new Set(subway_nm);

      subwayName = [...set];
    },
  })
  let html = '';
  let subrs = {};
  for (let j = 0; j < subwayName.length; j++) {
    let stationNm = encodeURI(subwayName[j]) 
    $.ajax({
    method: 'GET',
    url: 'http://127.0.0.1:8000/where/',
    headers: {
      'station': `${stationNm}`,
      'direction': 'right'
    },
    async: false,
    success: function(rs) {
      subrs[`${subwayName[j]}`] = rs['subways']
    }
  })
  }
  for (const key in subrs) {
    for (let index = 0; index < subrs[key].length; index++) {
      html += `<tr>`
      + `<td>${subrs[key][index]['subwayLineId']}</td>`
      + `<td>${subrs[key][index]['stationNm']}</td>`
      + `<td>${subrs[key][index]['finalStation']}</td>`
      + `<td>${subrs[key][index]['lineNm']}</td>`
      + `<td>${subrs[key][index]['lastest_station']}</td>`
      + `<td>${subrs[key][index]['state']}</td>`
      + `<td>${subrs[key][index]['goalTime']}</td>`
      + `</tr>`
    }
  }
  $("#subwayTbody").empty();
  $("#subwayTbody").append(html);
  return res;
};

function subwayPoint(result) {
  const seoulSubway = new ol.layer.Tile({
    name: 'subwayayer',
    source: new ol.source.TileWMS({
      url: "http://localhost:8081/geoserver/FirstProject/wms?service=WMS",
      params: {
        VERSION: "1.1.0",
        LAYERS: "FirstProject:subway_korea",
        BBOX: [
          126.40961456298828,35.033748626708984,129.24710083007812,37.96311950683594,
        ],
        SRS: "EPSG:4326",
        CQL_FILTER: `SBW_STATN_ADDR LIKE '%${result}%'`,
      },
      serverType: "geoserver",
      }),
  });

  map.getLayers().getArray()
  .filter(layer => layer.get('name') === 'subwayayer')
  .forEach(layer => map.removeLayer(layer));
  // content2.innerHTML = '';
  map.addLayer(seoulSubway)
  let rs = doCORSRequestSubway(result)
  }

window.addEventListener('resize', function () {
  const minZoom = 6;
  if (minZoom !== view.getMinZoom()) {
    view.setMinZoom(minZoom);
  }
});

function move() {
  map.beforeRender(
    ol.animation.pan({
      source: map.getView().getCenter(),
      duration: 1000,
    })
  );

  map
    .getView()
    .setCenter(
      new ol.geom.Point([126.95659953, 37.578220423]).getCoordinates()
    );

  map.getView().setZoom(parseInt(16));
}

let markerSource = new ol.source.Vector();

function addMarker(coordinate, name) {
  let point_feature = new ol.Feature({
    geometry: new ol.geom.Point([coordinate[0], coordinate[1]]),
    name: name,
  });

  markerSource.addFeature(point_feature);

  let markerStyle = new ol.style.Style({
    image: new ol.style.Icon({
      scale: 0.07,
      opacity: 1,
      anchor: [0.5, 512],
      anchorXUnits: 'fraction',
      anchorYUnits: 'pixels',
      src: '../img/pin.png',
    }),

    zindex: 10,
  });

  markerLayer = new ol.layer.Vector({
    source: markerSource,
    style: markerStyle,
  });

  map.addLayer(markerLayer);
}

map.on("click", function (evt) {
  let coordinate = evt.coordinate;
  i += 1;

  if(i > 1){
    markerLayer.getSource().clear();
  }
  addMarker(coordinate, i);
});

function markerDelete() {
  i = 0
  markerLayer.getSource().clear();
  content1.innerHTML = '';
  content2.innerHTML = '';
  map.getLayers().getArray()
.filter(layer => layer.get('name') === 'subwayayer')
.forEach(layer => map.removeLayer(layer));
}



reg = /[`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\\/   ]/gim;

map.on('singleclick', function (evt) {
  content1.innerHTML = '';
  
  var viewResolution = view.getResolution();
  let url = sigLayer.getSource().getFeatureInfoUrl(
      evt.coordinate, viewResolution, 'EPSG:3857',
      { 'INFO_FORMAT': 'text/html' });
  map.getView().setCenter(evt.coordinate);
  map.getView().setZoom(12)
  if (url) {
    content1.src = url
    // console.log(url.split('&')[9])
    function doCORSRequest(url){ 
      var res; 

      $.ajax({
        method: 'GET',
        url: url,
        async: false,
        success: function(result) {
          let data = result.replaceAll('td', '')
          res = data.replaceAll(reg, '').replaceAll('시', '시 ').split('\n')[49]
        },
      })
      return res;
      };

      let result = doCORSRequest(url)
      // console.log(result)
      subwayPoint(result)

  }
});

let positionFeature = new ol.Feature();
positionFeature.setStyle(new ol.style.Style({
    image: new ol.style.Circle({
        radius: 6,
        fill: new ol.style.Fill({
            color: '#3399CC'
        }),
        stroke: new ol.style.Stroke({
            color: '#fff',
            width: 2
        })
    })
}));

let geolocation = new ol.Geolocation({
  trackingOptions: {
      enableHighAccuracy: true
  },
  projection: view.getProjection()
});

geolocation.setTracking(true);

geolocation.on('change', function() {
  console.log('accuracy = ' + geolocation.getAccuracy() + 'm ' +
      'altitude = ' + geolocation.getAltitude() + 'm ' +
      'altitudeAccuracy = ' +  geolocation.getAltitudeAccuracy() + 'm ' +
      'heading = ' + geolocation.getHeading() + 'rad ' +
      'speed = ' + geolocation.getSpeed() + 'm/s');
});

geolocation.on('error', function(error) {
  console.log('geolocation error: ' + error.message);
});

geolocation.on('change:position', function() {
  var coordinates = geolocation.getPosition();
  positionFeature.setGeometry(coordinates ? new ol.geom.Point(coordinates) : null);
});

new ol.layer.Vector({
  map: map,
  source: new ol.source.Vector({
      features: [positionFeature]
  })
});