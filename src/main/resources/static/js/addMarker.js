import { map,LayerReset } from "/js/main.js";

let markerLayer;
let markerdict = [];
let markerList = []
let markerCoord = []

let markerSource = new ol.source.Vector({
    projection: 'EPSG:4326',
  });

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
  LayerReset()
}

export {markerList, markerLayer, markerdict, markerDel}