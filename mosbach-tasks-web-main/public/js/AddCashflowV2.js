let transactions = [];

document.getElementById('transaction-form').addEventListener('submit', function(e) {
    e.preventDefault();
    addTransaction();
});

function addTransaction() {
    const type = document.getElementById('type').value;
    const category = document.getElementById('category').value;
    const amount = parseFloat(document.getElementById('amount').value);
    const date = document.getElementById('date').value;
    const comments = document.getElementById('comments').value;

    if (!type || !category || isNaN(amount)) {
        alert("Bitte füllen Sie alle Felder korrekt aus.");
        return;
    }

    const newTransaction = { type, category, amount, date, comments };
    transactions.push(newTransaction);
    displayTransactions();
    updateSummary(type, amount);  
}

function displayTransactions() {
    const transactionsContainer = document.getElementById('transactions-container');
    transactionsContainer.innerHTML = '';
    
    transactions.forEach((transaction, index) => {
        const transactionDiv = document.createElement('div');
        transactionDiv.classList.add('transaction-item');
        transactionDiv.innerHTML = `
            <p><strong>Typ:</strong> ${transaction.type}</p>
            <p><strong>Kategorie:</strong> ${transaction.category}</p>
            <p><strong>Betrag:</strong> €${transaction.amount.toFixed(2)}</p>
            <p><strong>Datum:</strong> ${transaction.date}</p>
            <button onclick="deleteTransaction(${index})" class="delete-button">
                <span>&#128465;</span> <!-- Papierkorb-Symbol -->
            </button>
        `;
        transactionsContainer.appendChild(transactionDiv);
    });
}
function updateSummary(type, amount) {
    let totalIncome = parseFloat(document.getElementById('total-income').textContent);
    let totalExpenses = parseFloat(document.getElementById('total-expenses').textContent);

    if (type === 'income') {
        totalIncome += amount;
        document.getElementById('total-income').textContent = totalIncome.toFixed(2);
    } else if (type === ('fix costs' ||'variable costs')) {
        totalExpenses += amount;
        document.getElementById('total-expenses').textContent = totalExpenses.toFixed(2);
    }
}
function deleteTransaction(index) {
    transactions.splice(index, 1);
    displayTransactions();
}
document.addEventListener('DOMContentLoaded', function() {
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date').value = today;
});
function goBack() {
    window.location.href = '../Startpage.html';
}

$(document).ready(function() {
    $("#submit").click(function(event) {
        event.preventDefault(); // Verhindert das Standardverhalten des Formulars

        var cashflowData = {
            type: $("#type").val(),
            category: $("#category").val(),
            amount: parseFloat($("#amount").val()),
            date: $("#date").val(),
            paymentMethod: $("#paymentMethod").val(),
            repetition: $("#repetition").val(),
            comment: $("#comment").val()
        };

        // Einfacher Validierungscheck
        if (!cashflowData.type || !cashflowData.category || isNaN(cashflowData.amount) || !cashflowData.date || !cashflowData.paymentMethod || !cashflowData.repetition) {
            alert('Bitte alle erforderlichen Felder ausfüllen.');
            return;
        }

        console.log("Cashflow-Daten:", cashflowData);

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
                alert('Finanzfluss erfolgreich hinzugefügt.');
                // Optional: Seite neu laden oder Formular zurücksetzen
                location.reload();
            },
            error: function(xhr, ajaxOptions, thrownError) {
                console.log('Fehler: ' + xhr.status + ' ' + thrownError);
                alert('Ein Fehler ist beim Hinzufügen der Finanzen aufgetreten. Bitte versuche es erneut.');
            }
        });
    });
});