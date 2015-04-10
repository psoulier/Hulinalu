var map;

function initialize() {

  $('#search-input').focus();

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

function txrxScore(updateWidget, data, set) {
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
      set($(updateWidget));
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
    $(updateWidget).find('.uw-sel-' + score).prevAll().andSelf().children().children().addClass('sw-score-selected');
    $(updateWidget).find('.uw-sel-' + score).nextAll().first().first().removeClass('sw-score-selected');
  } else {
    $(updateWidget).children().first().first().removeClass('sw-score-selected');
  }

  if (userScore !== 0) {
    $(updateWidget).find('.uw-sel-' + userScore).prevAll().andSelf().children().addClass('sw-user-score-selected');
    $(updateWidget).find('.uw-sel-' + userScore).nextAll().children().removeClass('sw-user-score-selected');
  } else {
    $(updateWidget).children().first().removeClass('sw-user-score-selected');
  }
}

function ynw_set_score(updateWidget) {
  var score = $(updateWidget).data('fsr').score;
  var userScore = $(updateWidget).data('fsr').userScore;

  if (score == 0) {
    $(updateWidget).children().first().first().removeClass('sw-score-selected');
  } else if (score == 1) {
    $(updateWidget).find('.uw-sel-1').children().children().addClass('sw-score-selected');
    $(updateWidget).find('.uw-sel-2').children().children().removeClass('sw-score-selected');
  } else {
    $(updateWidget).find('.uw-sel-2').children().children().addClass('sw-score-selected');
    $(updateWidget).find('.uw-sel-1').children().children().removeClass('sw-score-selected');
  }

  if (userScore == 0) {
    $(updateWidget).children().first().removeClass('sw-user-score-selected');
  } else if (userScore == 1) {
    $(updateWidget).find('.uw-sel-1').children().addClass('sw-user-score-selected');
    $(updateWidget).find('.uw-sel-2').children().removeClass('sw-user-score-selected');
  } else {
    $(updateWidget).find('.uw-sel-2').children().addClass('sw-user-score-selected');
    $(updateWidget).find('.uw-sel-1').children().removeClass('sw-user-score-selected');
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

  txrxScore($(uw), $(outData), set_score);

  set_score($(uw));
});

$('.ynw-select').bind('click', function() {
  // TODO: this is fugly, clean this up.
  // TODO: make this a functio if the same as other
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

  txrxScore($(uw), $(outData), ynw_set_score);
});


$('.uw-select').hover(
    // Handles the mouseover
    function() {
      $(this).parent().find('.sw-score').addClass('sw-hidden');
      $(this).parent().find('.sw-user-score').removeClass('sw-user-score-selected');
      $(this).prevAll().andSelf().children().addClass('uw-select-over');
      $(this).nextAll().children().removeClass('uw-select-over'); 
    },
    // Handles the mouseout
    function() {
      $(this).parent().find('.sw-score').removeClass('sw-hidden');
      $(this).prevAll().andSelf().children().removeClass('uw-select-over');
      set_score($(this).parent());
      }
);

$('.ynw-select').hover(
    // Handles the mouseover
    function() {
      $(this).parent().find('.sw-score').addClass('sw-hidden');
      $(this).parent().find('.sw-user-score').removeClass('sw-user-score-selected');

      $(this).children().addClass('uw-select-over');
    },
    // Handles the mouseout
    function() {
      $(this).parent().find('.sw-score').removeClass('sw-hidden');
      $(this).children().removeClass('uw-select-over');
      ynw_set_score($(this).parent());
      }
);


$(document).ready( function() {

  $('.score-widget').each( function() {
    var widget = $(this).find('.uw-selector');
    var outData = {
      uw_id     : $(widget).attr('id'),
    };

    txrxScore($(widget), outData, set_score);
  });

  $('.yesno-widget').each( function() {
    var widget = $(this).find('.uw-selector');
    var outData = {
      uw_id     : $(widget).attr('id'),
    };

    txrxScore($(widget), outData, ynw_set_score);
  });
  

});


google.maps.event.addDomListener(window, 'load', initialize);
