$(document).ready(function() {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');

    if (token) {
        $.ajax({
            url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/auth/validate-email/'+token,
            type: 'POST',
            success: function (data) {
                console.log('Success:', data);
                $('#verified').show();
                $('#message').hide();
            },
            contentType: "application/json; charset=UTF-8",
            error: function (xhr, ajaxOptions, thrownError) {
                console.log('Error: ' + xhr.status + '   ' + thrownError);
                alert('An error occurred during verification. Please try again.');
            }
        });
    } else {
        $('#message').text('Invalid verification link.');
    }
});