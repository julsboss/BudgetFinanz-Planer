$('#confirmButton').on('click', function() {
    const month = $('#month').val();
    const year = $('#year').val();

    const postData = {
        month: month,
        year: year
    };

    $.ajax({
        url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/monthly-report',
        method: 'POST',
        headers: {
            'Authorization': localStorage.getItem('authToken'),
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(postData),
        success: function(response) {
            console.log('Monthly Report created:', response);
            alert('Monatsbericht erfolgreich erstellt.', response);
        },
        error: function(error) {
            console.error('Error creating report:', error);
            alert('Fehler beim Erstellen des Berichts.', error);
        }
    });
});













$('#confirmButton').on('click', function() {
    const month = $('#month').val();
    const year = $('#year').val();

    $.ajax({
        url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/monthly-report',
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('authToken')
        },
        data: { month: month, year: year },
        success: function(response) {
            console.log('Monthly Report retrieved:', response);
            populateReport(response);
        },
        error: function(error) {
            console.error('Error fetching report:', error);
            alert('Fehler beim Abrufen des Berichts.');
        }
    });
});

function populateReport(report) {
    $('#income-total').text(`${report.income_total.toFixed(2)}`);
    $('#fixed-total').text(`${report.fixed_total.toFixed(2)}`);
    $('#variable-total').text(`${report.variable_total.toFixed(2)}`);
    $('#total-income').text(`${report.income_total.toFixed(2)}`);
    $('#expenses-total').text(`${report.expenses_total.toFixed(2)}`);
    $('#difference-summary').text(`${report.difference_summary.toFixed(2)}`);

    populateCashflows('income-list', report.cashflows_income);
    populateCashflows('fixed-costs-list', report.cashflows_fixed_costs);
    populateCashflows('variable-costs-list', report.cashflows_variable_costs);
}

function populateCashflows(elementId, cashflows) {
    const listElement = $(`#${elementId}`);
    listElement.empty(); // Clear existing items
    cashflows.forEach(cashflow => {
        const cashflowItem = `
            <div class="cashflow-item">
                <p>${cashflow.category}: €${cashflow.amount.toFixed(2)}</p>
                <p>${cashflow.date}</p>
            </div>
        `;
        listElement.append(cashflowItem);
    });
}