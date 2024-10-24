document.getElementById('transaction-form').addEventListener('submit', function(e) {
    e.preventDefault();


    var cashflowData = {
        type: $("#type").val(),
        category: $("#category").val(),
        amount: parseFloat($("#amount").val()),
        date: $("#date").val(),
        payment_method: $("#payment-method").val(),
        repetition: $("#repetition").val(),
        comment: $("#comments").val()
    };




    // Einfacher Validierungscheck
    if (!cashflowData.type || !cashflowData.category || isNaN(cashflowData.amount) || !cashflowData.date || !cashflowData.payment_method || !cashflowData.repetition) {
        alert('Bitte alle erforderlichen Felder ausfüllen.');
        return;
    }

    //addTransactionToLocal(cashflowData);


    console.log("Cashflow-Daten:", cashflowData);
   // addTransaction();

    $.ajax({
        url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/cashflow', // Stelle sicher, dass die URL korrekt ist
        type: 'post',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        headers: {
            'Authorization': localStorage.getItem('authToken') // Token aus dem lokalen Speicher
        },
        data: JSON.stringify(cashflowData),
        success: function(data) {
            console.log('Finanzfluss erfolgreich hinzugefügt', data);
            alert(data.message);
            // Optional: Seite neu laden oder Formular zurücksetzen
            location.reload();
        },
        error: function(xhr, ajaxOptions, thrownError) {
            console.log('Fehler: ' + xhr.status + ' ' + thrownError);
            alert('Ein Fehler ist beim Hinzufügen der Finanzen aufgetreten. Bitte versuche es erneut.');
        }

    });

});














  const today = new Date().toISOString().split('T')[0];
  document.getElementById('date').value = today;



function goBack() {
    window.location.href = '../Startpage.html';
}
function goToNextPage(){
    window.location.href= './CashflowOverview.html'
}
