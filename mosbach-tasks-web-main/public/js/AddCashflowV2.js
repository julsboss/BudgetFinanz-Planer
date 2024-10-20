

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

/*
function addTransactionToLocal(transaction) {
    let transactions = JSON.parse(localStorage.getItem('transactions')) || [];
    transactions.push(transaction); // Füge die Transaktion hinzu
    localStorage.setItem('transactions', JSON.stringify(transactions)); // Speichere die Liste
    displayTransactions(); // Zeige die Transaktionen an
    updateSummary(transaction.type, transaction.amount); // Update die Zusammenfassung
}
    */

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


/*
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

*/


function displayTransactions() {
    const transactions = JSON.parse(localStorage.getItem('transactions')) || [];
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

/*
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

    */

document.addEventListener('DOMContentLoaded', function() {
    // Gesamteinkommen aus localStorage abrufen
    const totalIncome = localStorage.getItem('totalIncome') || '0.00';
    const totalExpenses = localStorage.getItem('totalExpenses') || '0.00';

    // Gesamteinkommen und -kosten im Bericht anzeigen
    document.getElementById('total-income-report').textContent = totalIncome;
    document.getElementById('total-expenses-report').textContent = totalExpenses; // Stelle sicher, dass du dieses Element in deiner HTML-Datei hast.

    displayTransactions();
    /*

    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date').value = today;
    */
});
/*
function deleteTransaction(index) {
    transactions.splice(index, 1);
    displayTransactions();
}
    */

function deleteTransaction(index) {
    let transactions = JSON.parse(localStorage.getItem('transactions')) || [];
    transactions.splice(index, 1);
    localStorage.setItem('transactions', JSON.stringify(transactions));  // Speichere die Änderungen
    displayTransactions();  // Zeigt die aktualisierten Transaktionen an
}

function goBack() {
    window.location.href = '../Startpage.html';
}