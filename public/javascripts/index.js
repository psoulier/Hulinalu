var map;

/**
 * Code to execute on page load.
 */
function initialize() {

  /* Set focus to any ".search-input" text form. Currently, there's only
   * ever one on a page, so don't need to be to picky.
   */
  var d = document.getElementById("search-input");
  console.log("I get here...right?");
  if (d.length == 0) {
	  console.log("WTF?!?");
  //$('#search-input').focus();

  var mapOptions = {
    zoom: 15,
    maxZoom: 21
  };


  /* This code effectively determines if the client has just landed on the main
   * index page. 
   */
  var indexPage = document.getElementById('update-loc');
  if (indexPage) {

    // Determine where the client is geographically located.
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {

        // Make an Ajax GET request to tell server where the client is.
        jsRoutes.controllers.Application.currentLocation(position.coords.latitude, position.coords.longitude).ajax({
          success: function(data) {
            $("#update-loc").html(data);
          },
          error: function() {
            alert("An error occurred trying to find your location. Sorry :(");
          }
        })


      }, function() {
        alert("An error occurred trying to send your location to Hulinalu. Sorry :(");
      });
    }
  }
}


// For google maps API.
google.maps.event.addDomListener(window, 'load', initialize);
