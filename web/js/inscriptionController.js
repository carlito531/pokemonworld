/**
 * Created by charly on 24/06/2016.
 */

$( "#inscription" ).click(function() {

    // get fields
    var login = '';
    var password = '';

    if ($('#loginText') != undefined && $('#loginText').val() != '' ) {
        login = $('#loginText').val();
    }

    if ($('#passwordText') != undefined && $('#passwordText').val() != '' ) {
        password = $('#passwordText').val();
    }

    var data = {
        'login' : login,
        'password' : password
    }

    // send datas to the symfony controller
    $.ajax({
        type: "POST",
        url: "/inscription/startInscriptionOrConnection",
        data: data,
        success: alert('succes')
    });
});

$( "#connection" ).click(function() {
    alert( "Handler for .click() called." );
});
