$(document).ready(function() {
    $("#submit").click(function(event) {
        event.preventDefault(); // Verhindert das Standardverhalten des Formulars (Seitenreload)

        var loginData = {
            email: $("#email").val(),
            password: $("#password").val()

        };

        // Einfacher Validierungscheck
        if (!loginData.email || !loginData.password) {
            alert('Bitte alle Felder ausfüllen.');
            return;
        }

        console.log("Login-Daten:", loginData);

        $.ajax({
            url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/auth/login', // Stelle sicher, dass die URL korrekt ist
            type: 'post',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',  // Setze den charset korrekt
            data: JSON.stringify(loginData),
            success: function(data) {
                if (data.token) {
                    localStorage.setItem('authToken', data.token); // Token speichern
                    console.log('Login erfolgreich', data);
                    window.location.href = '../subpages/Startpage.html'; // Weiterleitung auf die Dashboard-Seite
                } else {
                    console.log('Login fehlgeschlagen: Kein Token erhalten');
                    alert('Login fehlgeschlagen. Bitte überprüfe deine Zugangsdaten.');
                }
            },
            error: function(xhr, ajaxOptions, thrownError) {
                console.log('Fehler: ' + xhr.status + ' ' + thrownError);
                alert('Ein Fehler ist beim Login aufgetreten. Bitte versuche es erneut.');
            }
        });
    });
});
function goBack() {
    window.location.href = '../Homepage.html';
}
