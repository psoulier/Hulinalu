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



function set_score(upateWidget) {
  var p = $(updateWidget).find('.uw-selected').parent();

  p.prevAll().andSelf().children().addClass('uw-select-over');
  p.nextAll().children().removeClass('uw-select-over');
}

$('.uw-select').bind('click', function() {
  $(this).addClass('uw-selected');
  set_score($(this).parent().parent());
});

$('.uw-select').hover(
    // Handles the mouseover
    function() {
      $(this).prevAll().andSelf().children().addClass('uw-select-over');
      $(this).nextAll().children().removeClass('uw-select-over'); 
    },
    // Handles the mouseout
    function() {
      var selected_star = $(this).parent().has('.uw-selected');
      if (selected_star.length !== 0) {
        $(this).prevAll().andSelf().addClass('uw-select-over');
        $(this).nextAll().removeClass('uw-select-over'); 
      } else {
        $(this).prevAll().andSelf().removeClass('uw-select-over');
      }
    }
);

$(document).ready( function() {

  $('.uw-selector').each( function() {
    var out_data = {
      uw_id : $(this).attr('id')
    };

    var object = new Object();

    object.name = "Paul";

    var jsonData = JSON.stringify(object);


    console.log(jsonData);

    jsRoutes.controllers.Application.update().ajax({
      type : 'POST',
      dataType : 'json',
      data : jsonData,
      contentType : 'application/json; charset=utf-8',
      success : function(data) {
        console.log(data);
      },
      error : function(data) {
        alert("It no worky");
      }
    });

  });
});


google.maps.event.addDomListener(window, 'load', initialize);
