document.addEventListener('DOMContentLoaded', function() {
    const registrationForm = document.querySelector('form');

    registrationForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Verhindert das Standardverhalten (Seiten-Reload)

        // Eingabewerte abrufen
        const firstName = document.getElementById('firstName').value;
        const lastName = document.getElementById('lastName').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Einfacher Validierungscheck
        if (!firstName || !lastName || !email || !password) {
            alert('Bitte alle Felder ausfüllen.');
            return;
        }

        // Passwortstärke überprüfen (mindestens 6 Zeichen)
        if (password.length < 6) {
            alert('Das Passwort muss mindestens 6 Zeichen lang sein.');
            return;
        }

        // JSON-Payload für den Server erstellen
        const signUpData = {
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password
        };

        // AJAX-Anfrage zur Registrierung
        fetch('https://deine-api-url/api/auth/sign-up', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(signUpData)
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Registrierung fehlgeschlagen');
            }
        })
        .then(data => {
            console.log('Registrierung erfolgreich:', data);
            alert('Registrierung erfolgreich! Bitte prüfe deine E-Mails, um das Konto zu bestätigen.');
            window.location.href = 'login.html'; // Weiterleitung auf die Login-Seite
        })
        .catch(error => {
            console.error('Fehler:', error);
            alert('Registrierung fehlgeschlagen. Bitte überprüfe deine Eingaben und versuche es erneut.');
        });
    });
});
