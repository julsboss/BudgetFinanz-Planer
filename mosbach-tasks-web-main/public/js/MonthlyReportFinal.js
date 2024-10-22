$(document).ready(function() {
    $('#confirmButton').on('click', function() {
        const month = $('#month').val();
        const year = $('#year').val();
        
        if (year) {
            $.ajax({
                url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/monthly-report',
                method: 'GET',
                headers: {
                    'Authorization': localStorage.getItem('authToken')
                },
                data: { month: month, year: year },
                success: function(response) {
                    // Clear existing data
                    $('#income-list').empty();
                    $('#fixed-costs-list').empty();
                    $('#variable-costs-list').empty();

                    // Populate totals
                    $('#income-total').text(response.income_total.toFixed(2));
                    $('#fixed-total').text(response.fixed_total.toFixed(2));
                    $('#variable-total').text(response.variable_total.toFixed(2));
                    $('#expenses-total').text(response.expenses_total.toFixed(2));
                    $('#difference-summary').text(response.difference_summary.toFixed(2));

                    // Populate cashflows
                    populateCashflows('income-list', response.cashflows_income);
                    populateCashflows('fixed-costs-list', response.cashflows_fixed_costs);
                    populateCashflows('variable-costs-list', response.cashflows_variable_costs);
                },
                error: function(error) {
                    console.error('Error fetching report:', error);
                    alert('Fehler beim Abrufen des Berichts.');
                }
            });
        } else {
            alert('Bitte geben Sie ein gültiges Jahr ein.');
        }
    });

    function populateCashflows(elementId, cashflows) {
        const listElement = $(`#${elementId}`);
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
});