document.addEventListener('keydown', function(event) {
    if (event.ctrlKey && event.key === 'p') {
        event.preventDefault(); // Prevent the default print dialog
        convertHTMLToPDF(); // Call your function to save as PDF
    }
});

function convertHTMLToPDF() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

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