function epsg3857toEpsg4326(pos) {
  let x = pos[0];
  let y = pos[1];
  x = (x * 180) / 20037508.34;
  y = (y * 180) / 20037508.34;
  y = (Math.atan(Math.pow(Math.E, y * (Math.PI / 180))) * 360) / Math.PI - 90;
  if(pos.length > 2){
    let x1 = pos[2];
    let y1 = pos[3];
    x1 = (x1 * 180) / 20037508.34;
    y1 = (y1 * 180) / 20037508.34;
    y1 = (Math.atan(Math.pow(Math.E, y1 * (Math.PI / 180))) * 360) / Math.PI - 90;
    return [x, y, x1, y1];
  }else{
   return [x, y];
  }
  }


  function epsg4326toEpsg3857(pos) {
    let x = pos[0];
    let y = pos[1];
    x = (x * 20037508.34) / 180;
    y = Math.log(Math.tan(((90 + y) * Math.PI) / 360)) / (Math.PI / 180);
    y = (y * 20037508.34) / 180;
    return [x, y];
  }


  export {epsg3857toEpsg4326, epsg4326toEpsg3857}