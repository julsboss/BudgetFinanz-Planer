document.getElementById('monthlyReportForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const month = document.getElementById('month').value;
    const year = document.getElementById('year').value;

    // Perform validation
    if (!month || !year) {
        alert('Bitte Monat und Jahr wählen.');
        return;
    }

    // Fetch the monthly report from the API
    fetch(`/api/monthly-report?month=${month}&year=${year}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // Handle the data as needed
            // Display the report or update the UI accordingly
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
});

function goBack() {
    window.location.href = '../Startpage.html';
}