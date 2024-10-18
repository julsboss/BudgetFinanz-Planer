/*

document.addEventListener('keydown', function(event) {
    if (event.ctrlKey && event.key === 'p') {
        event.preventDefault(); // Prevent the default print dialog
        convertHTMLToPDF(); // Call your function to save as PDF
    }
});
*/
document.addEventListener('DOMContentLoaded', function() {
    updateReport();
    // Update der Werte, wenn die Auswahl geändert wird
    document.getElementById("month").addEventListener("change", updateReport);
    document.getElementById("year").addEventListener("change", updateReport);
});

// Beispiel für das Abrufen von gespeicherten Werten
function updateReport() {
    const month = document.getElementById("month").value;
    const year = document.getElementById("year").value;

    console.log(localStorage.getItem(`${year}-${month}`));

    // Hole die entsprechenden Werte für den Monat und das Jahr aus LocalStorage
    const totalIncome = getIncomeForMonth(year, month);
    const totalExpenses = getExpensesForMonth(year, month);

    // Update die Anzeige
    document.getElementById("total-income-report").textContent = totalIncome.toFixed(2);
    document.getElementById("total-expenses-report").textContent = totalExpenses.toFixed(2);
}

console.log(localStorage.getItem(`${year}-${month}`)); // Überprüfen, ob die Daten vorhanden sind


function convertHTMLToPDF() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    updateReport();

    // Select the content to convert
    const elementHTML = document.querySelector("#contentToPrint");



    // Convert HTML to PDF
    doc.html(elementHTML, {
        callback: function(doc) {
            doc.save('monthly-report.pdf');
        },
        margin: [10, 10, 10, 10],
        autoPaging: 'text',
        x: 0,
        y: 0,
        width: 190,
        windowWidth: 675
    });
}


// Funktion zum Abrufen von Einnahmen für den angegebenen Monat und Jahr
function getIncomeForMonth(year, month) {
    const key = `${year}-${month}`; // Generiere den Schlüssel für Jahr und Monat
    const cashflowData = localStorage.getItem(key) ? JSON.parse(localStorage.getItem(key)) : null;// Abrufen der Daten
    return cashflowData ? cashflowData.income : 0; // Gebe die Einnahmen oder 0 zurück, wenn keine Daten vorhanden sind
}

// Funktion zum Abrufen von Ausgaben für den angegebenen Monat und Jahr
function getExpensesForMonth(year, month) {
    const key = `${year}-${month}`; // Generiere den Schlüssel für Jahr und Monat
    const cashflowData = localStorage.getItem(key) ? JSON.parse(localStorage.getItem(key)) : null; // Abrufen der Daten
    return cashflowData ? cashflowData.expenses : 0; // Gebe die Ausgaben oder 0 zurück, wenn keine Daten vorhanden sind
}

doc.save(`${year}-${month}-report.pdf`);
