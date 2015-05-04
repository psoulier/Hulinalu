var map;


/**
 * Code to execute on page load.
 */
function initialize() {

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

$(document).ready(function() {
  /* Set focus to any ".search-input" text form. Currently, there's only
   * ever one on a page, so don't need to be to picky.
   */
  var d = $('.search-box');
  d = $(d).find('#search-input');
  d.focus();
});

// For google maps API.
google.maps.event.addDomListener(window, 'load', initialize);
