document.getElementById('transaction-form').addEventListener('submit', function(e) {
    e.preventDefault();

    const type = document.getElementById('type').value;
    const category = document.getElementById('category').value;
    const amount = parseFloat(document.getElementById('amount').value);

    if (!type || !category || isNaN(amount)) {
        alert("Bitte füllen Sie alle Felder korrekt aus.");
        return;
    }
    
    const transactionList = document.getElementById('transactions-list');
    
    const transactionItem = document.createElement('div');
    transactionItem.textContent = `${category}: €${amount.toFixed(2)} (${type})`;
    
    transactionList.appendChild(transactionItem);

    updateSummary(type, amount);
});

function updateSummary(type, amount) {
    let totalIncome = parseFloat(document.getElementById('total-income').textContent);
    let totalExpenses = parseFloat(document.getElementById('total-expenses').textContent);

    if (type === 'income') {
        totalIncome += amount;
        document.getElementById('total-income').textContent = totalIncome.toFixed(2);
    } else if (type === 'expense') {
        totalExpenses += amount;
        document.getElementById('total-expenses').textContent = totalExpenses.toFixed(2);
    }
}
document.addEventListener('DOMContentLoaded', function() {
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('date').value = today;
});

