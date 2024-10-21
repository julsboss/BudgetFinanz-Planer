function logout() {
    if (confirm('Möchten Sie sich wirklich abmelden?')) {
        $.ajax({
            url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/auth',
            method: 'DELETE',
            headers: {
                'Authorization': localStorage.getItem('authToken')
            },
            success: function(response) {
                localStorage.removeItem('authToken');
                console.log('Erfolgreich abgemeldet:', response);
                localStorage.removeItem('authToken'); // Remove token from storage
                window.location.href = 'LoginPage.html'; // Redirect to login page
            },
            error: function(error) {
                console.error('Fehler beim Abmelden:', error);
                alert('Abmeldung fehlgeschlagen. Bitte versuchen Sie es erneut.');
            }
        });
    }
}

// Bind the logout function to a button click
document.getElementById('logout').addEventListener('click', logout);

function goBack() {
    window.location.href = 'Startpage.html';
}