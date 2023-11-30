import { requestHospPhar } from "/js/request.js";
import { vworldLayer,sigLayer,HospLayer } from "/js/layers.js";
import { epsg3857toEpsg4326 } from "/js/changeEPSG.js";
import { HospPoint, round } from "/js/siguHPpoint.js";
import { markerLayer,markerList,markerDel } from "/js/addMarker.js";

// let rowNum = document.querySelector('#info .rNSelect')
// let cL = document.querySelector('#info .cLselect')
let btnMyLoc = document.querySelector('.btn-my-loc')
let markDel = document.querySelector('.markDel')
let hospLocbtn = document.querySelectorAll('.hospLoc')

let mapOverlay;
let hover = null;

let my_loc = [];
let my_loc3857 = [];

document.cookie = "safeCookie1=foo; SameSite=Lax"; 
document.cookie = "safeCookie2=foo"; 
document.cookie = "crossCookie=bar; SameSite=None; Secure";

const sources = new ol.source.OSM();

const view = new ol.View({
  center: ol.proj.fromLonLat([126.986, 37.541]),
  zoom: 10,
  minZoom: 6,
});

const map = new ol.Map({
  layers: [vworldLayer],
  target: "map",
  view: view,
});

let select = null;

function selectStyle(feature) {
  const color = feature.get('COLOR') || '#eeeeee';
  selected.getFill().setColor(color);
  return selected;
}

const selectSingleClick = new ol.interaction.Select({
  style: selectStyle
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

geolocation.on('error', function(error) {
  console.log('geolocation error: ' + error.message);
});

geolocation.on('change:position', function() {
  var coordinates = geolocation.getPosition();
  positionFeature.setGeometry(coordinates ? new ol.geom.Point(coordinates) : null);
  my_loc = epsg3857toEpsg4326(coordinates);
  my_loc3857 = coordinates;
});

function LayerReset(){
  map.getLayers().getArray()
  .filter(layer =>layer.get('id') === 'sigLayerCQL'|| layer.get('id') === 'HospLocLayer'|| layer.get('id') === 'HospMyLoc')
  .forEach(layer => map.removeLayer(layer));
}


for(let i of hospLocbtn){
  i.addEventListener('click', ()=>{
      var hospykiho = i.getAttribute("ykiho");
      console.log(hospykiho)
      getHospLoc(hospykiho)
  })
}

function getHospLoc(ykiho) {
  LayerReset()
  var cqlFilter;
  if(ykiho!=null){
    cqlFilter = "ykiho = '"+ykiho+"'";
  }
  console.log(cqlFilter)
  const HospLocLayer =  new ol.layer.Tile({
    id: 'HospLocLayer',
    opacity: 0.5,
    source: new ol.source.TileWMS({
      url: "http://localhost:8081/geoserver/FirstProject/wms?service=WMS",
      params: {
        VERSION: "1.1.0",
        LAYERS: "FirstProject:hospital",
        BBOX: [
          125.171875,33.22053909301758,129.5814971923828,38.47322463989258
        ],
        SRS: "EPSG:4326",
        CQL_FILTER: `${cqlFilter}`,
      },
      serverType: "geoserver",
    }),
  });
  map.addLayer(HospLocLayer)
  console.log(HospLocLayer.getSource().getExtent())
  // map.getView().setCenter(HospLocLayer.getSource().tmpExtent_);
  map.getView().setZoom(16);
}

btnMyLoc.addEventListener("click", ()=>{
  if(document.querySelector('.ol-popup') != null){
    document.querySelector('.ol-popup').remove()
  }
  if(document.querySelector('.mk-popup') != null){
    document.querySelector('.mk-popup').remove()
  }
  LayerReset()
  const HospMyLoc =  new ol.layer.Tile({
    id: 'HospMyLoc',
    opacity: 0.5,
    source: new ol.source.TileWMS({
      url: "http://localhost:8081/geoserver/FirstProject/wms?service=WMS",
      params: {
        VERSION: "1.1.0",
        LAYERS: "FirstProject:hosp",
        BBOX: [
          125.171875,33.22053909301758,129.5814971923828,38.47322463989258
        ],
        SRS: "EPSG:4326",
        CQL_FILTER: `BBOX(geom,${round[0]},${round[1]},${round[2]},${round[3]})`,
      },
      serverType: "geoserver",
    }),
  });
  map.addLayer(HospMyLoc)
  map.getView().setCenter(my_loc3857);
  map.getView().setZoom(13);
});

// map.on('click', async function(evt) {
//   map.getTargetElement().style.cursor = map.hasFeatureAtPixel(evt.pixel) ? 'pointer' : '';
//   if (hov !== null) {
//     hov = null;
//     pop.innerHTML = '';
//     pop.style.display = 'none';
//     pop.style.width = '0';
//     pop.style.height = '0'
//   }  
//   map.forEachFeatureAtPixel(evt.pixel, function(f) {
//     hov = f;
//     return true;
//   });
//   if(hov){
//     if(document.getElementsByClassName('mk-popup')[0] != null){
//       document.getElementsByClassName('mk-popup')[0].remove();
//     }
//     let coord = hov.values_.geometry.flatCoordinates;
//     pop = document.createElement("div");
//     pop.classList.add('mk-popup');
//     pop.innerHTML = '';
//     pop.innerHTML += '<p>병원명<span class="close_btn">X</span></p>'
//     let idx = 0;
//     let cnt = 0;
//     markerList.forEach(item=>{
//       var geoJSONFormat = new ol.format.GeoJSON();
//       var fe = geoJSONFormat.readFeature(markerList)
//       if(hov.ol_uid == Object.keys(item.getSource().uidIndex_)[idx] || 
//       hov.values_.geometry.flatCoordinates[0] == item.getSource().uidIndex_[`${Object.keys(item.getSource().uidIndex_)[idx]}`].values_.geometry.flatCoordinates[0]){
//         pop.innerHTML += `${item.get('name')}<br>`;
//         cnt ++;
//       }
//       idx += 1;
//     })
//     console.log(cnt)
//     pop.style.display = 'block';
//     pop.style.width = '400px';
//     pop.style.height = '300px';
    
//     let overlay = new ol.Overlay({
//       element: pop,
//       autoPan: true,
//       className: "multiPopup",
//       autoPanMargin: 100,
//       autoPanAnimation: {
//         duration: 400
//       }
//     });

//   overlay.setPosition(coord);
//   map.addOverlay(overlay);

//   let oElem = overlay.getElement();
//     oElem.addEventListener('click', function(e) {
//       var target = e.target;
//       if (target.className == "close_btn") {
//         map.removeOverlay(overlay);
//       }
//     });
//   }
// });

markDel.addEventListener('click',()=>{
  markerDel()
})

new ol.layer.Vector({
  map: map,
  source: new ol.source.Vector({
    features: [positionFeature]
  })
});






export { map, markerLayer, view, my_loc3857, LayerReset}