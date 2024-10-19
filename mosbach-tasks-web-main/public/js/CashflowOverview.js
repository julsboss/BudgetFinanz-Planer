
        document.addEventListener("DOMContentLoaded", function () {
            const token = localStorage.getItem('authToken');

            
            var transactions = []
            // Cashflows vom Server abrufen
    $.ajax({
        url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/cashflow/user', // Endpunkt für das Abrufen der Cashflows
        type: 'GET',
        dataType: 'json',
        headers: {
            'Authorization': token // Authorization Header hinzufügen
        },
        success: function (data) {
            transactions = data.cashflows || []; // Cashflows dem Array zuweisen
            localStorage.setItem('transactions', JSON.stringify(transactions)); // Optional: Cashflows im LocalStorage speichern
            displayTransactions(); // Transaktionen anzeigen
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log('Fehler beim Abrufen der Cashflows:', xhr.status, thrownError);
            alert('Fehler beim Abrufen der Cashflows. Bitte erneut versuchen.');
        }
    });


            const cashflowList = document.getElementById('cashflow-list');


            // Funktion zum Rendern der Transaktionen
            renderTransactions();

            // Funktion zum Rendern der Transaktionen
            function renderTransactions() {
                cashflowList.innerHTML = ''; // Leere die Tabelle

                transactions.forEach((transaction, index) => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${transaction.date}</td>
                        <td>${transaction.type}</td>
                        <td>${transaction.category}</td>
                        <td>€${transaction.amount.toFixed(2)}</td>
                        <td>${transaction.paymentMethod}</td>
                        <td>${transaction.comments}</td>
                        <td>
                            <button onclick="editTransaction(${index})">Bearbeiten</button>
                            <button onclick="deleteTransaction(${index})">Löschen</button>
                        </td>
                    `;
                    cashflowList.appendChild(row);
                });
            }

            // Funktion für das Bearbeiten von Transaktionen
            window.editTransaction = function (index) {
                const transaction = transactions[index];
                document.getElementById('edit-index').value = index;
                document.getElementById('edit-date').value = transaction.date;
                document.getElementById('edit-type').value = transaction.type;
                document.getElementById('edit-category').value = transaction.category;
                document.getElementById('edit-amount').value = transaction.amount;
                document.getElementById('edit-paymentMethod').value = transaction.paymentMethod;
                document.getElementById('edit-comments').value = transaction.comments;

                document.getElementById('editModal').style.display = 'block';
            };

            // Funktion zum Schließen des Modals
            window.closeModal = function () {
                document.getElementById('editModal').style.display = 'none';
            };

            // Formular zur Bearbeitung der Transaktion absenden
            document.getElementById('edit-transaction-form').addEventListener('submit', function (e) {
                e.preventDefault();

                const index = document.getElementById('edit-index').value;
                const updatedTransaction = {
                    date: document.getElementById('edit-date').value,
                    type: document.getElementById('edit-type').value,
                    category: document.getElementById('edit-category').value,
                    amount: parseFloat(document.getElementById('edit-amount').value),
                    paymentMethod: document.getElementById('edit-paymentMethod').value,
                    comments: document.getElementById('edit-comments').value,
                };

            // Rufe die AJAX-Funktion auf, um die Transaktion zu aktualisieren
             updateTransaction(transactions[index].id, updatedTransaction);

                closeModal();
            });

            // Funktion zum Löschen von Transaktionen
            window.deleteTransaction = function (index) {
                const transactionId = transactions[index].id;
                transactions.splice(index, 1);
                deleteTransaction(transactionId);
                renderTransactions();
            };

            // Funktion zum Zurückgehen
            window.goBack = function () {
                window.history.back();
            };

            function updateTransaction(transactionId, transactionData) {
                $.ajax({
                    url: `https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/cashflow/${transactionId}`, // URL zum Update-Endpoint
                    type: 'PUT',
                    dataType: 'JSON',
                    contentType: 'application/json',
                    headers: {
                        'Authorization': localStorage.getItem('authToken') // Token aus dem localStorage
                    },
                    data: JSON.stringify({ token: localStorage.getItem('authToken'), cashflow: transactionData }),
                    processData: false,
                    success: function(data) {
                        $("#serverAnswer").html(data.message);
                        alert('Transaktion erfolgreich aktualisiert!');
                        renderTransactions(); // Aktualisiere die Anzeige der Transaktionen
                    },
                    error: function(xhr, ajaxOptions, thrownError) {
                        handleAjaxError(xhr, thrownError);
                    }
                });
            }

            function deleteTransaction(transactionId) {
                $.ajax({
                    url: `https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/cashflow/${transactionId}`, // URL zum Delete-Endpoint
                    type: 'DELETE',
                    headers: {
                        'Authorization': localStorage.getItem('authToken') // Token aus dem localStorage
                    },
                    success: function(data) {
                        $("#serverAnswer").html(data.message);
                        alert('Transaktion erfolgreich gelöscht!');
                    },
                    error: function(xhr, ajaxOptions, thrownError) {
                        handleAjaxError(xhr, thrownError);
                    }
                });
            }

            function handleAjaxError(xhr, thrownError) {
                if (xhr.responseText) {
                    try {
                        const response = JSON.parse(xhr.responseText);
                        alert('Fehler: ' + (response.message || 'Unbekannter Fehler'));
                    } catch (e) {
                        console.error("Parsing error:", e);
                        alert('Fehler: Die Antwort konnte nicht verarbeitet werden. ' + thrownError);
                    }
                } else {
                    alert('Fehler: Keine Antwort vom Server erhalten.');
                }
            }
        });
 