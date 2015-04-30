


(function() {

  var first = document.getElementById("firstName");
  var last = document.getElementById("lastName");
  var email = document.getElementById("email");
  var password = document.getElementById("password");
  var confirmPassword = document.getElementById("confirmPassword");
  var form = document.getElementById("new-account");

  if (!form)
  alert("wtf!?!");

  var firstOk = true;
  var lastOk = true;
  var confirmVisited = false;

  var checkFirst = function(e) {
    var ok = false;

    if (!first.value) {
      $(first).addClass('na-has-error');
      firstOk = false;
    }
    else {
      $(first).removeClass('na-has-error');
      firstOk = true;
    }

    if (!firstOk || !lastOk) {
      $('#error-name').css('display', '');
    }
    else {
      $('#error-name').css('display', 'none');
      ok = true;
    }

    return ok;
  }

  var checkLast = function(e) {
    var ok = false;

    if (!last.value) {
      $(last).addClass('na-has-error');
      lastOk = false;
    }
    else {
      $(last).removeClass('na-has-error');
      lastOk = true;
    }

    if (!firstOk || !lastOk) {
      $('#error-name').css('display', '');
    }
    else {
      $('#error-name').css('display', 'none');
      ok = true;
    }

    return ok;
  }

   


  var checkEmail = function(e) {
    var ok = false;

    if (!email.value) {
      $(email).addClass('na-has-error');
      $('#error-email').css('display', '');
    }
    else {
      $(email).removeClass("na-has-error");
      $('#error-email').css('display', 'none');
      ok = true;
    }

    return ok;
  }


  var checkPassword = function(e) {
    var ok = false;

    if (!password.value) {
      $('#error-password-missing').css('display', '');
      $('#error-confirm-missing').css('display', 'none');
      $('#error-password-mismatch').css('display', 'none');

      $(password).addClass('na-has-error');
      $(confirmPassword).removeClass('na-has-error');
    }
    else if (!confirmPassword.value) {
      if (confirmVisited) {
        $('#error-password-missing').css('display', 'none');
        $('#error-confirm-missing').css('display', '');
        $('#error-password-mismatch').css('display', 'none');

        $(password).removeClass('na-has-error');
        $(confirmPassword).addClass('na-has-error');
      }

      console.log("confirm visisted");
      confirmVisited = true;
    } 
    else if (password.value != confirmPassword.value && confirmVisited) {
      $('#error-password-missing').css('display', 'none');
      $('#error-confirm-missing').css('display', 'none');
      $('#error-password-mismatch').css('display', '');

      $(password).addClass('na-has-error');
      $(confirmPassword).addClass('na-has-error');
    }
    else {
      $('#error-password-missing').css('display', 'none');
      $('#error-confirm-missing').css('display', 'none');
      $('#error-password-mismatch').css('display', 'none');

      $(password).removeClass('na-has-error');
      $(confirmPassword).removeClass('na-has-error');

      ok = true;
    }

    return ok;
  }
  

  first.addEventListener('blur', checkFirst, false);
  last.addEventListener('blur', checkLast, false);
  email.addEventListener('blur', checkEmail, false);
  password.addEventListener('blur', checkPassword);
  confirmPassword.addEventListener('blur', checkPassword);


  form.addEventListener('submit', function(e) {
    var prevent = false;

    if (!checkFirst(null)) prevent = true;
    if (!checkLast(null)) prevent = true;
    if (!checkEmail(null)) prevent = true;
    if (!checkPassword(null)) prevent = true;
    
    console.log("submitted");

    if (prevent) {
      e.preventDefault();
    }

  });


}());
