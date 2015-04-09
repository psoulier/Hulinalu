var map;

function initialize() {
  var mapOptions = {
    zoom: 15,
    maxZoom: 21
  };

  var locMap = document.getElementById('location-map-canvas');

  if (locMap) {
    var lat = locMap.getAttribute('lat');
    var lng = locMap.getAttribute('lng');
    var pos = new google.maps.LatLng(lat, lng);
    var mapOptions = {
      zoom: 15,
      maxZoom: 21
    };
    

    map = new google.maps.Map(locMap, mapOptions);
    var marker = new google.maps.Marker({map : map, position: pos});
    map.setCenter(pos);

  }

  var indexPage = document.getElementById('map-canvas');
  if (indexPage) {

    map = new google.maps.Map(indexPage,
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
            // FIXME: Issue #10
            alert("Oh darn!");
          }
        })


      }, function() {
        // FIXME: Issue #10
      });
    }
  }

  $('.carousel').carousel('pause');

}

function txrxScore(updateWidget, data) {
  var jsonData = JSON.stringify(data);


  // Send POST request to get data for this update widget.
  jsRoutes.controllers.Application.update().ajax({
    type : 'POST',
    dataType : 'json',
    data : jsonData,
    contentType : 'application/json; charset=utf-8',
    success : function(inData) {
      console.log(inData);
      $(updateWidget).data('fsr', inData);
      set_score($(updateWidget));
    },
    error : function(outData) {
      alert("It no worky");
    }
  });
}


function set_score(updateWidget) {
  var score = $(updateWidget).data('fsr').score;
  var userScore = $(updateWidget).data('fsr').userScore;

  if (score !== 0) {
    $(updateWidget).find('.uw-sel-' + score).prevAll().andSelf().children().addClass('uw-selected');
    $(updateWidget).find('.uw-sel-' + score).nextAll().children().removeClass('uw-selected');
  } else {
    $(updateWidget).children().removeClass('uw-selected');
  }

  if (userScore !== 0) {
    $(updateWidget).find('.uw-sel-' + userScore).prevAll().andSelf().addClass('uw-user-selected');
    $(updateWidget).find('.uw-sel-' + userScore).nextAll().removeClass('uw-user-selected');
  } else {
    $(updateWidget).children().removeClass('uw-user-selected');
  }  
}

$('.uw-select').bind('click', function() {
  // TODO: this is fugly, clean this up.
  var uw = $(this).parent();
  var s = $(this).attr('class');
  var n = s.indexOf('uw-sel-');
  var uscore = s.charAt(n+7);

  console.log("user score=" + uscore);

  var outData = {
    uw_id     : $(uw).attr('id'),
    userScore : uscore
  };

  console.log("click" + $(outData));

  txrxScore($(uw), $(outData));

  set_score($(uw));
});

$('.uw-select').hover(
    // Handles the mouseover
    function() {
      $(this).parent().find('.uw-selected').removeClass('uw-selected');
      $(this).prevAll().andSelf().children().addClass('uw-select-over');
      $(this).nextAll().children().removeClass('uw-select-over'); 
    },
    // Handles the mouseout
    function() {
      $(this).prevAll().andSelf().children().removeClass('uw-select-over');
      set_score($(this).parent());
      }
);


$(document).ready( function() {

  $('.uw-selector').each( function() {
    var outData = {
      uw_id     : $(this).attr('id'),
    };

    txrxScore($(this), outData);
  });

  $('#search-input').focus();

});


google.maps.event.addDomListener(window, 'load', initialize);
