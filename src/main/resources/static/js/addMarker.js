import { map } from "/js/main.js";

let markerLayer;
let markerdict = [];
let markerList = []
let markerCoord = []

let markerSource = new ol.source.Vector({
    projection: 'EPSG:4326',
  });

// function addMarker(coordinate, list) {
//   if(coordinate[0] == 'null' || coordinate[1] == 'null'){
//     return null;
//   }

//   let x = Number(coordinate[0]);
//   let y = Number(coordinate[1]);
//   let pos = []
//   pos = [x,y]
//   x = (x * 20037508.34) / 180;
//   y = Math.log(Math.tan(((90 + y) * Math.PI) / 360)) / (Math.PI / 180);
//   y = (y * 20037508.34) / 180;

//   var point_feature = new ol.Feature({
//       geometry: new ol.geom.Point([x, y])
//   });
  
//   markerSource.addFeature(point_feature);

//   let markerStyle = new ol.style.Style({
//     image: new ol.style.Icon({
//         opacity: 1,
//         scale: 0.05,
//         src: '../img/pin.png'
//     }),
//     zindex: 10
// });

// markerLayer = new ol.layer.Vector({
//     name: coordinate[2],
//     source: markerSource,
//     style: markerStyle 
// });
// list.push(markerLayer)
// markerList = list
// markerCoord.push([x,y])
// markerdict.push({
//   name: coordinate[2],
//   x: x,
//   y: y
// })
// map.addLayer(markerLayer);
// }

function markerDel(){
  markerList = []
  if(markerLayer != undefined){
    markerLayer.getSource().clear();
  }
  if(document.querySelector('.ol-popup') != null){
    document.querySelector('.ol-popup').remove()
  }
  if(document.querySelector('.mk-popup') != null){
    document.querySelector('.mk-popup').remove()
  }
  $("#hospTbody").empty();
  map.getLayers().getArray().filter(layer => layer.get('id') === 'sigLayerCQL'|| layer.get('id') === 'HospMyLoc')
  .forEach(layer => map.removeLayer(layer));
}

export {markerList, markerLayer, markerdict, markerDel}