

$(function() {
  $('input').focus(function() {
    $("#location-name").show();
  }).blur(function() {
    $("#location-name").hide();
  });
});

$(function() {
  $('textarea').focus(function() {
    $("#location-description").show();
  }).blur(function() {
    $("#location-description").hide();
  });
});

