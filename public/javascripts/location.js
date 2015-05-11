var hoveringScoreWidget = null;

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
  var votes = $(updateWidget).data('fsr').totalVotes;
  var scoreList = $(updateWidget).data('fsr').scoreList.split(';');
  var scoreLabels = $(updateWidget).data('fsr').scoreLabels.split(';');
  var totalVotes = $(updateWidget).data('fsr').totalVotes;
  var scoreSelect = $(updateWidget).find('.score-' + userScore);


  var alreadySelected = updateWidget.find('.score-selected');

  if (alreadySelected.length) {
    if (alreadySelected === scoreSelect) {
      /*
      $(this).toggleClass('score-selected');
      uscore = 0;
      */
    }
    else {
      alreadySelected.toggleClass('score-selected');
      scoreSelect.toggleClass('score-selected');
    }
  }
  else {
    $(scoreSelect).toggleClass('score-selected');
  }

  
  $(updateWidget).find('.score-votes').text(votes + ' Vote' + ((votes != 1) ? 's' : ''));
  var circle = $(updateWidget).find('.score-circle');
  $(circle).find('p').text(score);


  var value = $(updateWidget).find('.score-value');
  value.text(scoreLabel);

  var label = $(updateWidget).find('.score-circle-value');
  var labelList = $(updateWidget).data('fsr').scoreLabels.split(';');

  if (hoveringScoreWidget == null) {
    if (userScore > 0) {
      label.text(labelList[userScore-1]);
      $(updateWidget).find('.score-' + userScore).addClass('score-selected');
    }
    else {
      label.text('');
    }
  }

  for (var i = 0; i < scoreList.length; i++) {
    var bar = updateWidget.find('.bar-' + (i+1));
    var percent = scoreList[i] / totalVotes;

    bar.find('.bar-label').text(scoreLabels[i]);
    bar.find('.bar-full').css('width', (percent*100) + '%');
    bar.find('.bar-count').text(scoreList[i] + ' Votes');
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

  $('.score-stats-toggle').bind('click', function() {
    $(this).parent().find('.score-stats').toggleClass('score-stats-hidden');
  });

  // Only bind events if updates haven't been disabled (usually because no user
  // is signed in).
  if ( $("#location-page").length > 0 && $("#location-page").hasClass("updates-disabled") == false ) {


    $('.score-select-circle').bind('click', function() {
      var scoreWidget = $(this).closest('.score-widget');
      var uscore = $(this).attr('class').charAt(6);


      // Setup data to send to server.
      var outData = {
        uw_id     : $(scoreWidget).attr('id'),
        userScore : uscore
      };

      // Send data to server and get updated info.
      txrxScore(scoreWidget, outData, set_score);
    });


    $('.score-select-circle').hover(
      // Handles the mouseover
      function() {
        var label = $(this).parent().parent().find('.score-circle-value');
        var labelList = $(this).closest('.score-widget').data('fsr').scoreLabels.split(';');

        var score = parseInt($(this).attr('class').charAt(6));
        label.text(labelList[score-1]);
        hoveringScoreWidget = this;
      },
      // Handles the mouseout
      function() {
        var label = $(this).parent().parent().find('.score-circle-value');
        var scoreWidget = $(this).closest('.score-widget');
        var labelList = $(scoreWidget).data('fsr').scoreLabels.split(';');
        var userScore = parseInt($(scoreWidget).data('fsr').userScore);

        if (userScore > 0) {
          label.text(labelList[userScore-1]);
        }
        else {
          label.text('');
        }

        hoveringScoreWidget = null;
      }
    );



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

$(document).ready(function() {
  /* Set focus to any ".search-input" text form. Currently, there's only
   * ever one on a page, so don't need to be to picky.
   */
  var searchInput = $('#search-input');
  var inputLen = searchInput.val().length * 2;

  $(searchInput).focus();
  searchInput[0].setSelectionRange(inputLen, inputLen);
});

