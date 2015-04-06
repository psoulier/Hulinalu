var map;

function initialize() {
  var mapOptions = {
    zoom: 15,
    maxZoom: 21
  };
      console.log("Here I AM!!!!!");
  
  map = new google.maps.Map(document.getElementById('map-canvas'),
		  mapOptions);
      console.log("Here I AM!!!!!");

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = new google.maps.LatLng(position.coords.latitude,
			  position.coords.longitude);

      var infowindow = new google.maps.InfoWindow({
			  map: map,
		      position: pos,
		      content: 'You are here.'
		  });

      map.setCenter(pos);
      
      console.log("Here I AM!!!!!");

      jsRoutes.controllers.Application.currentLocation(position.coords.latitude, position.coords.longitude).ajax({
			  success: function(data) {
				  $("#update-loc").html(data);
			  },
			  error: function() {
				  alert("Oh darn!");
			  }
		  })


	  }, function() {
	  });
  }   

 }

google.maps.event.addDomListener(window, 'load', initialize);
