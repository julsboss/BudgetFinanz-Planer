let totalIncome = 0;
let totalExpenses = 0;

function addIncome() {
    const description = document.getElementById('income-description').value;
    const amount = parseFloat(document.getElementById('income-amount').value);

    if (description && !isNaN(amount)) {
        totalIncome += amount;
        updateDisplay();
        clearInputs('income');
    }
}

function addExpense() {
    const description = document.getElementById('expense-description').value;
    const amount = parseFloat(document.getElementById('expense-amount').value);

    if (description && !isNaN(amount)) {
        totalExpenses += amount;
        updateDisplay();
        clearInputs('expense');
    }
}

function updateDisplay() {
    document.getElementById('total-income').innerText = totalIncome.toFixed(2) + ' €';
    document.getElementById('total-expenses').innerText = totalExpenses.toFixed(2) + ' €';
    
    const balance = totalIncome - totalExpenses;
    document.getElementById('balance').innerText = balance.toFixed(2) + ' €';
}

function clearInputs(type) {
    if (type === 'income') {
        document.getElementById('income-description').value = '';
        document.getElementById('income-amount').value = '';
    } else if (type === 'expense') {
        document.getElementById('expense-description').value = '';
        document.getElementById('expense-amount').value = '';
    }
}