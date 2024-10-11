$("#submit").click(function() {

    // Verhindert das Standardverhalten des Buttons
    event.preventDefault(); 

    var registrationData = {
        firstName: $("#vorname").val(),
        lastName: $("#nachname").val(),
        email: $("#email").val(),
        password: $("#password").val()
    };

    // Einfacher Validierungscheck
    if (!registrationData.firstName || !registrationData.lastName || !registrationData.email || !registrationData.password) {
        alert('Bitte alle Felder ausfüllen.');
        return;
    }

    // Passwortstärke überprüfen (mindestens 6 Zeichen)
    if (registrationData.password.length < 6) {
        alert('Das Passwort muss mindestens 6 Zeichen lang sein.');
        return;
    }

    console.log(registrationData);

    $.ajax({
        url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/auth/sign-in',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            $("#serverAnswer").html(data.message);
            alert('Registrierung erfolgreich! Bitte prüfe deine E-Mails, um das Konto zu bestätigen.');
            window.location.href = 'login.html'; // Weiterleitung auf die Login-Seite
        },
        data: JSON.stringify(registrationData),
        processData: false,
        error: function(xhr, ajaxOptions, thrownError) {
            alert('Fehler: ' + xhr.status + ' ' + thrownError);
        }
    });
});

