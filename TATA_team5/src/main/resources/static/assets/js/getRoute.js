function flyToLocation(long, lat) {
    map.flyTo({
        center: [long, lat],
        zoom: 10
    });

}

function getRoute(x,y,p,r) {

    var a=parseFloat(x);
    var b=parseFloat(y);
    var c=parseFloat(p);
    var d=parseFloat(r);
    var start=[a,b];
    var end=[c,d];
    flyToLocation(a,b);
    var ruta = "route" + Math.floor(Math.random()*100);
    var directionsRequest = 'https://api.mapbox.com/directions/v5/mapbox/driving/' + a + ',' + b + ';' + c + ',' + d + '?steps=true&geometries=geojson&access_token=' + mapboxgl.accessToken;
    $.ajax({
        method: 'GET',
        url: directionsRequest,
    }).done(function(data) {
        var route = data.routes[0].geometry;
        map.addLayer({
            id: ruta,
            type:'line',
            source: {
                type: 'geojson',
                data: {
                    type: 'Feature',
                    geometry: route
                }
            },
            paint: {
                'line-width': 2
            }
        });

        var marker1 = new mapboxgl.Marker();
        marker1.setLngLat([a, b]);
        marker1.addTo(map);
        var marker2 = new mapboxgl.Marker();
        marker2.setLngLat([c, d]);
        marker2.addTo(map);
        var markerList = mapboxgl.Marker();
        markerList.append(marker1);
        markerList.append(marker2);
        map.fitToView(markerList);

// this is where the JavaScript from the next step will go
    });

}