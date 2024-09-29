document.getElementById('transaction-form').addEventListener('submit', function(e) {
    e.preventDefault();

    const type = document.getElementById('type').value;
    const category = document.getElementById('category').value;
    const amount = parseFloat(document.getElementById('amount').value);
    const date = document.getElementById('date').value;
    const comments = document.getElementById('comments').value;

    if (!type || !category || isNaN(amount)) {
        alert("Bitte füllen Sie alle Felder korrekt aus.");
        return;
    }
    
    
    const transactionList = document.getElementById('transactions-list');
    
    /* nicht notwendig const transactionItem = document.createElement('div');
    transactionItem.textContent = `${category}: €${amount.toFixed(2)} (${type})`;
    
    transactionList.appendChild(transactionItem);*/

    updateSummary(type, amount);  
    addTransaction(type, category, amount, date, comments);
});
function addTransaction(type, category, amount, date) {
    const transactionsContainer = document.getElementById('transactions-container');

    const transactionDiv = document.createElement('div');
    transactionDiv.classList.add('transaction-item');

    transactionDiv.innerHTML = `
        <p><strong>Typ:</strong> ${type}</p>
        <p><strong>Kategorie:</strong> ${category}</p>
        <p><strong>Betrag:</strong> €${amount.toFixed(2)}</p>
        <p><strong>Datum:</strong> ${date}</p>
            
    `;

    transactionsContainer.appendChild(transactionDiv);
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
document.addEventListener('DOMContentLoaded', function() {
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date').value = today;
});


