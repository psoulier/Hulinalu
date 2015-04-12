var map;

/**
 * Code to execute on page load.
 */
function initialize() {

  /* Set focus to any ".search-input" text form. Currently, there's only
   * ever one on a page, so don't need to be to picky.
   */
  $('#search-input').focus();

  var mapOptions = {
    zoom: 15,
    maxZoom: 21
  };

  /* This was code to place a map of clients current location. Saving, because I want
   * to use later.
   */
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

  //$('.carousel').carousel('pause');
}

/**
 * Helper function to transmit and recieve data for a feautre.
 * @param updateWidget the widget being updated.
 * @param data Data to send.
 * @param set Function to call in order to update widget display.
 */
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
      alert("Unable to update score widget. *sniff* :'(");
    }
  });
}

/**
 * Sets the little buttons for a scaled score widget.
 *
 * The HTML associated with this is in ScoreWidget.scala.html.
 *
 * @param updateWidget Widget to update.
 */
function set_score(updateWidget) {
  var score = $(updateWidget).data('fsr').score;
  var userScore = $(updateWidget).data('fsr').userScore;

  // Only update score indicators if there's data.
  if (score !== 0) {
    // Due to the HTML to get the desired CSS effects, need to drill down a few levels
    // of the DOM to access the elements we want.
    $(updateWidget).find('.uw-sel-' + score).prevAll().andSelf().children().children().addClass('sw-score-selected');
    $(updateWidget).find('.uw-sel-' + score).nextAll().first().first().removeClass('sw-score-selected');
  } else {
    // No data for this score, so clear all the indicator dots.
    $(updateWidget).children().first().first().removeClass('sw-score-selected');
  }

  // Same as score, except for the indicator for the user-selected score.
  if (userScore !== 0) {
    $(updateWidget).find('.uw-sel-' + userScore).prevAll().andSelf().children().addClass('sw-user-score-selected');
    $(updateWidget).find('.uw-sel-' + userScore).nextAll().children().removeClass('sw-user-score-selected');
  } else {
    $(updateWidget).children().first().removeClass('sw-user-score-selected');
  }
}

/**
 * Sets the yes/no widget indicators.
 *
 * The HTML associated to this is in YesNoWidget.scala.html.
 *
 * @param updateWidget Widget to update.
 */
function ynw_set_score(updateWidget) {
  var score = $(updateWidget).data('fsr').score;
  var userScore = $(updateWidget).data('fsr').userScore;

  // This is really just a two-way radio button, but with five states since both the
  // actual status and the user status is displayed.
  if (score == 0) {
    // No score, turn off indicator
    $(updateWidget).children().first().first().removeClass('sw-score-selected');
  } else if (score == 1) {
    // Value is yes, turn this indicator on and the other one off.
    $(updateWidget).find('.uw-sel-1').children().children().addClass('sw-score-selected');
    $(updateWidget).find('.uw-sel-2').children().children().removeClass('sw-score-selected');
  } else {
    // Opposite here.
    $(updateWidget).find('.uw-sel-2').children().children().addClass('sw-score-selected');
    $(updateWidget).find('.uw-sel-1').children().children().removeClass('sw-score-selected');
  }

  // Same as above, but for user indicator.
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

/**
 * Handles the 'click' event for the little indicator/selector buttons in
 * a score scale.
 */
$('.uw-select').bind('click', function() {
  // Each of the five buttons has a 'uw-sel-x' class (where x = [1,5]). We
  // figure out a numerical value for which button was clicked by getting 
  // the 'x' part of uw-sel-x.
  var uw = $(this).parent();
  var s = $(this).attr('class');
  var n = s.indexOf('uw-sel-');
  var uscore = s.charAt(n+7);

  // Setup data to send to server.
  var outData = {
    uw_id     : $(uw).attr('id'),
    userScore : uscore
  };

  // Send data to server and get updated info.
  txrxScore($(uw), $(outData), set_score);
});

/**
 * Handles a 'click' event for the yes/no toggle widget.
 */
$('.ynw-select').bind('click', function() {
  // Each of the five buttons has a 'uw-sel-x' class (where x = [1,5]). We
  // figure out a numerical value for which button was clicked by getting 
  // the 'x' part of uw-sel-x.
  var uw = $(this).parent();
  var s = $(this).attr('class');
  var n = s.indexOf('uw-sel-');
  var uscore = s.charAt(n+7);

  // Send data to server and get updated info.
  var outData = {
    uw_id     : $(uw).attr('id'),
    userScore : uscore
  };

  // Update the widget display.
  txrxScore($(uw), $(outData), ynw_set_score);
});


/**
 * Handles hover events for the score buttons.
 *
 * This provides a basic scoring appearance where the lower buttons are
 * highlighted and the upper are turned off (thing of the little star
 * scoring widgets on a lot of web sites).
 */
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

/**
 * Handles hover events for the yes/no widget.
 *
 * Just highlights the current selection and unhighlights the other.
 */
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

/**
 * JQuery init function that is called when the document is ready.
 */
$(document).ready( function() {
  /* Update all the score widgets. This generates a bunch of POST requests
   * on page load.
   */
  $('.score-widget').each( function() {
    var widget = $(this).find('.uw-selector');
    var outData = {
      uw_id     : $(widget).attr('id'),
    };

    txrxScore($(widget), outData, set_score);
  });

  /* Same for the yes/no widgets. Go grab data from the servers with 
   * POST requets.
   */
  $('.yesno-widget').each( function() {
    var widget = $(this).find('.uw-selector');
    var outData = {
      uw_id     : $(widget).attr('id'),
    };

    txrxScore($(widget), outData, ynw_set_score);
  });
});

// For google maps API.
google.maps.event.addDomListener(window, 'load', initialize);
