/*
 * check input fields for connection
 */
$( "#connection" ).click(function(event) {

    // check if all values are complete
    var name = '';
    var login = '';
    var password = '';

    name = $("#nameText").val();
    login = $("#loginText").val();
    password = $("#passwordText").val();

    if ($("#chkInscription").is(":checked")) {

        // cancel the sumbit button event
        if (name == "" || login == "" || password == "") {
            event.preventDefault();

            // display error message if value missing
            if (name == "") {
                $("#errMsg").show();
                $("#errMsg").val("pseudo manquant ");
            }
        }
    } else {
        if (login == "" || password == "") {
            event.preventDefault();
        }
    }

    if (login == "") {
        $("#errMsg").show();
        $("#errMsg").val($("#errMsg").val() + "login manquant ");
    }
    if (password == "") {
        $("#errMsg").show();
        $("#errMsg").val($("#errMsg").val() + "mot de passe manquant ");
    }
});


/*
 * check if it is an inscription
 */
$("#chkInscription").click(function() {

    if($(this).is(":checked")) {
        $("#nameGrp").show();
        $("#connection").prop('value', 'Inscription');
    } else {
        $("#nameGrp").hide();
        $("#connection").prop('value', 'Connexion');
    }
});