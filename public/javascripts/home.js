var map;

function initialize() {
  var mapOptions = {
    zoom: 15,
    maxZoom: 21
  };
  
  map = new google.maps.Map(document.getElementById('map-canvas'),
		  mapOptions);

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var pos = new google.maps.LatLng(position.coords.latitude,
			  position.coords.longitude);

      var infowindow = new google.maps.Marker({
		map: map,
		      position: pos
		  });

      map.setCenter(pos);
      

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
