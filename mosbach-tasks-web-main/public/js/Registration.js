$("#submit").click(function(event) {

    // Verhindert das Standardverhalten des Buttons
    event.preventDefault(); 

    var registrationData = {
        firstName: $("#vorname").val(),
        lastName: $("#nachname").val(),
        email: $("#email").val(),
        password: $("#password").val()
    };

    // Ausgabe in einer bestimmten Reihenfolge
console.log([
    { key: 'firstName', value: registrationData.firstName },
    { key: 'lastName', value: registrationData.lastName },
    { key: 'email', value: registrationData.email },
    { key: 'password', value: registrationData.password }
]);

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

    if (!/^\d+$/.test(registrationData.password)) {
        alert('Das Passwort darf nur Zahlen enthalten.');
        return;
    }

    console.log(registrationData);

    $.ajax({
        url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/auth/sign-up',
        type: 'POST',
        dataType: 'JSON',
        contentType: 'application/json',
        data: JSON.stringify(registrationData),
        processData: false,
        success: function(data) {
            $("#serverAnswer").html(data.message);
            alert('Registrierung erfolgreich! ');
            window.location.href = 'LoginPage.html'; // Weiterleitung auf die Login-Seite
        },
        
        error: function(xhr, ajaxOptions, thrownError) {
            // Wenn der Server eine Antwort zurücksendet
            if (xhr.responseText) {
                try {
                    const response = JSON.parse(xhr.responseText); // Versuche, die Antwort zu parsen
                    console.log("Response Text: ", response); // Antwort für Debugging ausgeben
                    alert('Fehler: ' + (response.message || 'Unbekannter Fehler'));
                } catch (e) {
                    // Fehler beim Parsen der Antwort
                    console.error("Parsing error:", e);
                    alert('Fehler: Die Antwort konnte nicht verarbeitet werden. ' + thrownError);
                }
            } else {
                // Wenn keine Antwort vom Server erhalten wurde
                alert('Fehler: Keine Antwort vom Server erhalten.');
            }
        }
    });
});
function goBack() {
    window.location.href = '../Homepage.html';
}
