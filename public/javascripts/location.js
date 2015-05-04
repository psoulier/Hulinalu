
/**
 * Code to execute on page load.
 */
function initialize() {

  /* Set focus to any ".search-input" text form. Currently, there's only
   * ever one on a page, so don't need to be to picky.
   */
  $('#search-input').focus();

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
  var scoreLabel = $(updateWidget).data('fsr').scoreLabel;
  var selector = $(updateWidget).find('.uw-selector');
  var scores = [];
  var total = 0.0;

  
  var scoreList = $(updateWidget).data('fsr').scoreList.split(';');
  for (var i = 0; i < scoreList.length; i++) {
    scores.push( parseFloat(scoreList[i]) );
    total += scores[i];
  }

  // Avoid divide-by-zero 
  if (total == 0) {
    total = 1;
  }

  var barGraph = $(updateWidget).find('.score-bar-graph');
  for (var i = 0; i < scores.length; i++) {
    var bottomBar = (scores[i] / total) * 100.0;
    var topBar = 100.0 - bottomBar;
    var bar = $(barGraph).find('.bar-' + (i + 1));

    $(bar).find('.bar-top').css('height', topBar + '%');
    $(bar).find('.bar-bottom').css('height', bottomBar + '%');
  }

  /*
  var accuracy = $(updateWidget).find('.score-accuracy');
  var valueBar = $(accuracy).find('.value');
  switch (parseInt($(updateWidget).data('fsr').accuracy)) {
    case 0:
      $(valueBar).attr('class', 'value accuracy-color-poor');
      $(accuracy).find('.value').css('width', '0%');
      $(accuracy).find('.space').css('width', '100%');
      break ;
    case 1:
      $(valueBar).attr('class', 'value accuracy-color-poor');
      $(accuracy).find('.value').css('width', '20%');
      $(accuracy).find('.space').css('width', '80%');
      break ;
    case 2:
      $(valueBar).attr('class', 'value accuracy-color-marginal');
      $(accuracy).find('.value').css('width', '40%');
      $(accuracy).find('.space').css('width', '60%');
      break ;
    case 3:
      $(valueBar).attr('class', 'value accuracy-color-average');
      $(accuracy).find('.value').css('width', '60%');
      $(accuracy).find('.space').css('width', '40%');
      break ;
    case 4:
      $(valueBar).attr('class', 'value accuracy-color-good');
      $(accuracy).find('.value').css('width', '80%');
      $(accuracy).find('.space').css('width', '20%');
      break ;
    case 5:
      $(valueBar).attr('class', 'value accuracy-color-excellent');
      $(accuracy).find('.value').css('width', '100%');
      $(accuracy).find('.space').css('width', '0%');
      break ;
    default:
      console.log("treall?");
      break ;
  }
  */

  var circle = $(updateWidget).find('.score-circle');
  $(circle).find('p').text(score);


  var value = $(updateWidget).find('.score-value');
  value.text(scoreLabel);

  // Only update score indicators if there's data.
  if (userScore !== 0) {
    // Due to the HTML to get the desired CSS effects, need to drill down a few levels
    // of the DOM to access the elements we want.
    $(selector).find('.uw-sel-' + userScore).prevAll().andSelf().children().children().addClass('sw-score-selected');
    $(selector).find('.uw-sel-' + userScore).nextAll().children().children().removeClass('sw-score-selected');
  } else {
    // No data for this score, so clear all the indicator dots.
    $(selector).children().first().first().removeClass('sw-score-selected');
  }

  // Same as score, except for the indicator for the user-selected score.
  /*
  if (userScore !== 0) {
    $(selector).find('.uw-sel-' + userScore).prevAll().andSelf().children().addClass('sw-user-score-selected');
    $(selector).find('.uw-sel-' + userScore).nextAll().children().removeClass('sw-user-score-selected');
  } else {
    $(selector).children().first().removeClass('sw-user-score-selected');
  }
  */
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
  var selector = $(updateWidget).find('.uw-selector');

  if (selector.length == 0) {
    console.log("OH CRAP!");
    console.log(selector);
  }

  // This is really just a two-way radio button, but with five states since both the
  // actual status and the user status is displayed.
  if (score == 0) {
    // No score, turn off indicator
    $(selector).children().first().first().removeClass('sw-score-selected');
  } else if (score == 1) {
    // Value is yes, turn this indicator on and the other one off.
    $(selector).find('.uw-sel-1').children().children().addClass('sw-score-selected');
    $(selector).find('.uw-sel-2').children().children().removeClass('sw-score-selected');
  } else {
    // Opposite here.
    $(selector).find('.uw-sel-2').children().children().addClass('sw-score-selected');
    $(selector).find('.uw-sel-1').children().children().removeClass('sw-score-selected');
  }

  // Same as above, but for user indicator.
  if (userScore == 0) {
    $(selector).children().first().removeClass('sw-user-score-selected');
  } else if (userScore == 1) {
    $(selector).find('.uw-sel-1').children().addClass('sw-user-score-selected');
    $(selector).find('.uw-sel-2').children().removeClass('sw-user-score-selected');
  } else {
    $(selector).find('.uw-sel-2').children().addClass('sw-user-score-selected');
    $(selector).find('.uw-sel-1').children().removeClass('sw-user-score-selected');
  }
}

/**
 * Handles the 'click' event for the little indicator/selector buttons in
 * a score scale.
 */

function pollForUpdates() {
  $('.score-widget').each( function() {
    var widget = $(this);
    var outData = {
      uw_id     : $(widget).attr('id'),
      userScore : "0"
    };

    txrxScore($(widget), outData, set_score);
  });

  $('.yesno-widget').each( function() {
    var widget = $(this);
    var outData = {
      uw_id     : $(widget).attr('id'),
      userScore : "0"
    };

    txrxScore($(widget), outData, ynw_set_score);
  });
    

  setTimeout(pollForUpdates, 5000);
}

function onClick() {
  // Each of the five buttons has a 'uw-sel-x' class (where x = [1,5]). We
  // figure out a numerical value for which button was clicked by getting 
  // the 'x' part of uw-sel-x.
  var uw = $(this).parent().parent().parent();
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
}


/**
 * JQuery init function that is called when the document is ready.
 */
$(document).ready( function() {

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

  /* Update all the score widgets. This generates a bunch of POST requests
   * on page load.
   */
  $('.score-widget').each( function() {
    var widget = $(this);
    var outData = {
      uw_id     : $(widget).attr('id'),
      userScore : "0"
    };

    txrxScore($(widget), outData, set_score);
  });

  /* Same for the yes/no widgets. Go grab data from the servers with 
   * POST requets.
   */
  $('.yesno-widget').each( function() {
    var widget = $(this);
    var outData = {
      uw_id     : $(this).attr('id'),
      userScore : "0"
    };

    txrxScore($(widget), outData, ynw_set_score);
  });

  // Only bind events if updates haven't been disabled (usually because no user
  // is signed in).
  if ( $("#location-page").length > 0 && $("#location-page").hasClass("updates-disabled") == false ) {

    $('.uw-select').bind('click', function() {
      // Each of the five buttons has a 'uw-sel-x' class (where x = [1,5]). We
      // figure out a numerical value for which button was clicked by getting 
      // the 'x' part of uw-sel-x.
      var uw = $(this).parent().parent().parent();
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

    $('.ynw-select').bind('click', onClick);

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
        set_score($(this).parent().parent().parent());
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
        ynw_set_score($(this).parent().parent().parent());
        }
    );
  }
  else {
    $('.score-widget').find('.uw-main').css('display', 'none');
  }
  
  setTimeout(pollForUpdates, 5000);
});


google.maps.event.addDomListener(window, 'load', initialize);
