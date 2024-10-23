$(document).ready(function() {
    // Fetch and display user profile
    function fetchUserProfile() {
        $.ajax({
            url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/user',
            method: 'GET',
            headers: {
                'Authorization': localStorage.getItem('authToken')
            },
            success: function(data) {
                $('#id').val(data.userID);
                $('#firstName').val(data.firstName);
                $('#lastName').val(data.lastName);
                $('#email').val(data.email);
                $('#password').val(data.password);
            },
            error: function(error) {
                console.error('Fehler beim Abrufen der Profildaten:', error);
            }
        });
    }

    // Update user profile
    $('#profileForm').submit(function(event) {
        event.preventDefault();
        const updatedUser = {
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            email : $('#email').val(),
            passwort : $('#password').val()
        };
        
        $.ajax({
            url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/user',
            method: 'PUT',
            headers: {
                'Authorization': localStorage.getItem('authToken')
            },
            data: JSON.stringify(updatedUser),
            contentType: 'application/json',
            success: function(response) {
                alert(`User erfolgreich aktualisiert:`, response);
                
            },
            error: function(error) {
                console.error(`Aktualisierung nicht erfolgreich:`, error);
            },
        });
    });

    // Delete user profile
    $('#deleteProfile').click(function() {
        if (confirm('Are you sure you want to delete your profile?')) {
            $.ajax({
                url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/user',
                method: 'DELETE',
                headers: {
                    'Authorization': localStorage.getItem('authToken')
                },
                success: function(response) {
                    console.log(`User erfolgreich gelöscht:`, response);
                    window.location.href = '../Homepage.html';
                },
                error: function(error) {
                    console.error('Fehler beim Löschen des Profils:', error);
                }
            });
        }
    });

    // Initial fetch
    fetchUserProfile();
});
function goBack() {
    window.location.href = '../Startpage.html';
}
