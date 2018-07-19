
mapboxgl.accessToken = 'pk.eyJ1IjoibWVnYXJhbWkiLCJhIjoiY2pqcGtxdmJ5N3llcjNrcDEyY2UxdWZyZyJ9.aPCrM0aKAkSEXfcW2c0EOw';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v10',
    center: [18.4131,43.8563],
    zoom: 15
});
map.on('load', function() {
    getRoute(center[0], center[1], center[0]+0.001, center[1]+0.02);
});
