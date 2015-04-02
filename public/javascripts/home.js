var map;

function initialize() {
  var mapOptions = {
    zoom: 19,
    maxZoom: 21
  };
  
  map = new google.maps.Map(document.getElementById('map-canvas'),
		  mapOptions);

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

      jsRoutes.controllers.Application.currentLocation(position.coords.latitude, position.coords.longitude).ajax({
			  success: function(data) {
				  $("#current-loc").html(data);
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
