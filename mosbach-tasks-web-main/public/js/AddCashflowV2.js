let transactions = JSON.parse(localStorage.getItem('transactions')) || [];

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

    if (!type || !category || isNaN(amount) || !date) {
        alert("Bitte füllen Sie alle Felder korrekt aus.");
        return;
    }

    const newTransaction = { type, category, amount, date, comments };
    transactions.push(newTransaction);

     localStorage.setItem('transactions', JSON.stringify(transactions));

    displayTransactions();
    updateSummary(type, amount);  
}

// Funktion zum Speichern von Cashflow-Daten
function saveCashflowData(year, month, income, expenses) {
    const key = `${year}-${month}`;
    const cashflowData = {
        income: income,
        expenses: expenses
    };
    localStorage.setItem(key, JSON.stringify(cashflowData));
    console.log(`Daten gespeichert für ${key}:`, cashflowData);
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

    // Hole das Datum aus dem Date-Input
    const dateValue = document.getElementById('date').value;
    
    // Überprüfe, ob ein Datum ausgewählt wurde
    if (!dateValue) {
        alert("Bitte wählen Sie ein Datum aus.");
        return;
    }

    // Extrahiere Monat und Jahr aus dem Datum
    const date = new Date(dateValue);
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Monate sind 0-basiert, daher +1
    const year = date.getFullYear();
    //Initisalisere Gesamteinkommen und -ausgaben

    let totalIncome = parseFloat(localStorage.getItem('totalIncome')) || 0;
    let totalExpenses = parseFloat(localStorage.getItem('totalExpenses')) || 0;

    if (type === 'income') {
        totalIncome += amount;
        // Speichern in localStorage
        localStorage.setItem('totalIncome', totalIncome.toFixed(2));
        document.getElementById('total-income').textContent = totalIncome.toFixed(2);


    } else if (type === 'fix costs' || type == 'variable costs') {
        totalExpenses += amount;
        localStorage.setItem('totalExpenses', totalExpenses.toFixed(2));
        document.getElementById('total-expenses').textContent = totalExpenses.toFixed(2);
    }


    //Speichern der Cashflow-Daten

    saveCashflowData(year, month, totalIncome, totalExpenses);
}




document.addEventListener('DOMContentLoaded', function() {
    // Gesamteinkommen aus localStorage abrufen
    const totalIncome = localStorage.getItem('totalIncome') || '0.00';
    const totalExpenses = localStorage.getItem('totalExpenses') || '0.00';

    // Gesamteinkommen und -kosten im Bericht anzeigen
    document.getElementById('total-income-report').textContent = totalIncome;
    document.getElementById('total-expenses-report').textContent = totalExpenses; // Stelle sicher, dass du dieses Element in deiner HTML-Datei hast.

    displayTransactions();

    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date').value = today;
});

function deleteTransaction(index) {
    transactions.splice(index, 1);
    displayTransactions();
}

function goBack() {
    window.location.href = '../Startpage.html';
}
function goToNextPage() {
    window.location.href = 'CashflowOverview.html'; // Ersetze 'nextpage.html' mit dem Zielpfad
}

$(document).ready(function() {
    $("#submit").click(function(event) {
        event.preventDefault(); // Verhindert das Standardverhalten des Formulars

        var cashflowData = {
            token: localStorage.getItem('authToken'),
            cashflow:{
            type: $("#type").val(),
            category: $("#category").val(),
            amount: parseFloat($("#amount").val()),
            date: $("#date").val(),
            paymentMethod: $("#paymentMethod").val(),
            repetition: $("#repetition").val(),
            comment: $("#comment").val() || "none"
            }
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