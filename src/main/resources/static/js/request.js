import { map, markerLayer, view } from "/js/main.js";

let rowNum = document.querySelector('#info .rNSelect')
let cL = document.querySelector('#info .cLselect')
let radiusSelect = document.querySelector('#info .radius-select')

let rowN = 10;
rowNum.addEventListener("change", ()=>{
  rowN = rowNum.value;
});

let clcd = '병원';
cL.addEventListener("change", ()=>{
  clcd = cL.value;
});

let radius = 500;
radiusSelect.addEventListener("change", ()=>{
  radius = radiusSelect.value;
});

function requestHospPhar(result, type){ 
    let html = '';
    let thead = '';
    let markerList = [];
    let encoded = encodeURI(result);
    let res2;
    if(type == 'A'){
      const sigHosp =  new ol.layer.Tile({
        id: 'sigLayerCQL',
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
            CQL_FILTER: `sgg_nm = '${result}'`,
            FEATURE_COUNT: 5,
          },
          serverType: "geoserver",
        }),
      });
      map.addLayer(sigHosp)
    }else{
      
    }
  };



export {requestHospPhar}