
import { map, markerLayer, view, my_loc3857,LayerReset } from "/js/main.js";
import { requestHospPhar } from "/js/request.js";
import { epsg3857toEpsg4326 } from "/js/changeEPSG.js";
import { vworldLayer,sigLayer,HospLayer } from "/js/layers.js";

let sigu;
let round;

window.onload = function(){
  const circleFeature = new ol.Feature({
    geometry: new ol.geom.Circle(my_loc3857, 2500),
  });
  
  circleFeature.setStyle(
    new ol.style.Style({
      renderer(coordinates, state) {
        const [[x, y], [x1, y1]] = coordinates;
        const ctx = state.context;
        const dx = x1 - x;
        const dy = y1 - y;
        const radius = Math.sqrt(dx * dx + dy * dy);
  
        const innerRadius = 0;
        const outerRadius = radius * 1.4;
  
        const gradient = ctx.createRadialGradient(
          x,
          y,
          innerRadius,
          x,
          y,
          outerRadius
        );
        gradient.addColorStop(0, 'rgba(95,198,240 ,0)');
        gradient.addColorStop(0.6, 'rgba(95,198,240,0.2)');
        gradient.addColorStop(1, 'rgba(95,198,240,0.8)');
        ctx.beginPath();
        ctx.arc(x, y, radius, 0, 2 * Math.PI, true);
        ctx.fillStyle = gradient;
        ctx.fill();
  
        ctx.arc(x, y, radius, 0, 2 * Math.PI, true);
        ctx.strokeStyle = 'rgba(95,198,240,1)';
        ctx.stroke();
      },
    })
  )
  const radius = new ol.layer.Vector({
    name: 'radius',
    source: new ol.source.Vector({
      features: [circleFeature],
    })
  });
  
  map.addLayer(radius);
  const circle = new ol.geom.Circle(my_loc3857, 2500)

  round = epsg3857toEpsg4326(circle.getExtent())

  map.on('singleclick', function (evt) {
  var viewResolution = view.getResolution();
  let url = sigLayer.getSource().getFeatureInfoUrl(
      evt.coordinate, viewResolution, 'EPSG:3857',
      { 'INFO_FORMAT': 'application/json' });
  let res_api;
  if (url) {
    function doCORSRequest(url){ 
      var res;  
      $.ajax({
        method: 'GET',
        url: url,
        async: false,
        success: function(result) {
          var geoJSONFormat = new ol.format.GeoJSON();
          let data = geoJSONFormat.readFeatures(result)[0].getProperties()['sig_kor_nm']
          let properties = geoJSONFormat.readFeatures(result)[0].getProperties()
          console.log(properties)
          res = data
          res_api = data.replace(/시|광역|특별|자치/g, '').replace(' ','')
        },
      })
      return res;
    };
    let result = doCORSRequest(url)
    sigu = result
    HospPoint(result, res_api)
  }
  if(document.getElementsByClassName('ol-popup')[0] != null){
      document.getElementsByClassName('ol-popup')[0].remove();
  }
  if(document.querySelector('.mk-popup') != null){
    document.querySelector('.mk-popup').remove()
  }
  var coordinate = evt.coordinate;
  let element = document.createElement("div");
  element.classList.add('ol-popup');
  element.innerHTML = `<a id="popup-closer" class="ol-popup-closer"></a> <div><p>${sigu}</code></div>`;
  element.style.display = 'block';
  let overlay = new ol.Overlay({
      element: element,
      autoPan: true,
      className: "multiPopup",
      autoPanMargin: 100,
      autoPanAnimation: {
      duration: 400
    }
  });

  overlay.setPosition(coordinate);
  map.addOverlay(overlay);
  });
}

function HospPoint(result, res) {
  const sigLayerCQL =  new ol.layer.Vector({
    source: vectorSource,
    id : "sigLayerCQL",
    style: new ol.style.Style({
      stroke: new ol.style.Stroke({
          color: 'rgba(0,0,0, 0.4)',
          width: 2
      }),
      fill: new ol.style.Fill({
          color: '#ffffff40'
      })
    })
  });
  LayerReset()
  map.addLayer(sigLayerCQL)
  var vectorSource = new ol.source.Vector({
    loader: function(extent, resolution, projection) {
      let vec_url = 'http://localhost:8081/geoserver/FirstProject/wfs?'
      + 'service=WFS&version=1.1.0&request=GetFeature&'
      + 'typename=FirstProject:sido_fixed&outputFormat=application/json&'
      + 'srsname=EPSG:3857&'
      + `cql_filter=sig_kor_nm='${result}'`
      $.ajax({
        url: vec_url,
        success: function(data){
          var geoJSONFormat = new ol.format.GeoJSON();
          var feature = geoJSONFormat.readFeatures(data);
          console.log(data)
          vectorSource.addFeatures(feature);
          if(feature.length!=0){
            var extent = ol.extent.createEmpty();
            ol.extent.extend(extent, vectorSource.getExtent());
            map.getView().fit(extent, map.getSize());
          }
        }
      })
    }
  });
  map.getLayers().forEach(function(layer){
	  if(layer.get("id")=="sigLayerCQL"){
	      layer.setSource(vectorSource);
	  }
	});
    
  
  if( markerLayer != undefined ){
    markerLayer.getSource().clear();
  }
  let rs = requestHospPhar(result, 'A')
}
  
window.addEventListener('resize', function () {
  const minZoom = 6;
  if (minZoom !== view.getMinZoom()) {
    view.setMinZoom(minZoom);
  }
});

export{HospPoint, round}