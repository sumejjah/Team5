
mapboxgl.accessToken = 'pk.eyJ1IjoibWVnYXJhbWkiLCJhIjoiY2pqcGtxdmJ5N3llcjNrcDEyY2UxdWZyZyJ9.aPCrM0aKAkSEXfcW2c0EOw';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v10',
    center: [18.4131,43.8563],
    zoom: 15
});
map.on('load', function() {
    getRoute(18.41, 43.85, 18.43, 43.82);
});
