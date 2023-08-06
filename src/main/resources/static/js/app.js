function initMap(latt, lang, name){

    // Map option

    var options = {
        center: {lat:latt , lng:lang},
        zoom: 10
    }

    //New Map
    map = new google.maps.Map(document.getElementById("map"),options)

    //listen for click on map location

    google.maps.event.addListener(map, "click", (event) => {
        //add Marker
        addMarker({location:event.latLng});
    })



    //Marker

    const marker = new google.maps.Marker({
    position:{lat: latt, lng: lang},
    map:map,
    icon:"https://img.icons8.com/nolan/2x/marker.png"
    });

    //InfoWindow

    const detailWindow = new google.maps.InfoWindow({
        content: name
    });

    marker.addListener("mouseover", () =>{
        detailWindow.open(map, marker);
    })
    

    // loop through marker
    for (let i = 0; i < MarkerArray.length; i++){
        addMarker(MarkerArray[i]);

    }


}