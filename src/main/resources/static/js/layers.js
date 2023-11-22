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
	name: 'sigLayer',
    opacity: 0.5,
    source: new ol.source.TileWMS({
      url: "http://localhost:8081/geoserver/FirstProject/wms?service=WMS",
      params: {
        VERSION: "1.1.0",
        LAYERS: "FirstProject:sido_fixed",
        BBOX: [
          746110.2515145557,1458754.0441563793,1387947.6818815223,2068443.9546290115
        ],
        SRS: "EPSG:5179",
      },
      serverType: "geoserver",
    }),
});

const HospLayer =  new ol.layer.Tile({
  id: 'HospLayer',
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
    },
    serverType: "geoserver",
  }),
});


export {vworldLayer, sigLayer, HospLayer}