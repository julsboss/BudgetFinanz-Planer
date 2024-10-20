document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem('authToken');
    if (!token) {
        alert('Nicht authentifiziert. Bitte einloggen.');
        window.location.href = 'login.html'; // Weiterleitung zur Login-Seite
    }
    let transactions = []

            

    // Cashflows vom Server abrufen
    $.ajax({
        url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/cashflow/user', // Endpunkt für das Abrufen der Cashflows
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        headers: {
            'Authorization': token // Authorization Header hinzufügen
        },
        success: function (data) {
        console.log('Server Antwort:', data); // Prüfe die Antwort
            // Überprüfen, ob die Antwort die erwarteten Daten enthält
                        if (data && data.cashflow && Array.isArray(data.cashflow)) {
                            transactions = data.cashflow; // Cashflows dem Array zuweisen
                             console.log('Transaktionen:', transactions);
                            displayTransactions(); // Transaktionen anzeigen

                        } else if (data.message) {
                                  console.log('Nachricht vom Server:', data.message); // Zeigt die Nachricht an
                       } else {
                                  console.log('Keine Cashflows gefunden.');
                       }
        },
        error: function (xhr, ajaxOptions, thrownError) {

            console.log('Fehler beim Abrufen der Cashflows:', xhr.status, thrownError);
            alert('Fehler beim Abrufen der Cashflows. Bitte erneut versuchen.');
        }
    });
    const cashflowList = document.getElementById('cashflow-list');

    // Funktion zum Rendern der Transaktionen
        function displayTransactions() {
            cashflowList.innerHTML = ''; // Zunächst die Tabelle leeren

            if (transactions.length > 0) {
                transactions.forEach(transaction => {
                    const row = document.createElement('tr'); // Eine neue Zeile erstellen

                    // Spalten für die verschiedenen Attribute der Transaktion erstellen
                    const dateCell = document.createElement('td');
                    dateCell.textContent = transaction.date; // Datum hier anpassen, je nach Serverantwort
                    row.appendChild(dateCell);

                    const typeCell = document.createElement('td');
                    typeCell.textContent = transaction.type;
                    row.appendChild(typeCell);

                    const categoryCell = document.createElement('td');
                    categoryCell.textContent = transaction.category;
                    row.appendChild(categoryCell);

                    const amountCell = document.createElement('td');
                    amountCell.textContent = transaction.amount.toFixed(2); // Betrag formatieren
                    row.appendChild(amountCell);

                    const paymentMethodCell = document.createElement('td');
                    paymentMethodCell.textContent = transaction.payment_method; // Hier den Namen des Feldes anpassen
                    row.appendChild(paymentMethodCell);

                    const commentsCell = document.createElement('td');
                    commentsCell.textContent = transaction.comment; // Kommentare oder leer, falls nicht vorhanden
                    row.appendChild(commentsCell);

                    // Aktionen hinzufügen, z.B. Bearbeiten oder Löschen
                    const actionsCell = document.createElement('td');
                    actionsCell.innerHTML = `<button data-id="${transaction.id}" onclick="editTransaction(${transaction.id})">Bearbeiten</button>
                                             <button data-id="${transaction.id}" onclick="deleteTransaction(${transaction.id})">Löschen</button>`;
                    row.appendChild(actionsCell);

                    cashflowList.appendChild(row); // Die Zeile zur Tabelle hinzufügen
                });
            } else {
                console.log('Keine Cashflows gefunden oder unerwartete Antwort.'); // Hier wird eine Warnung ausgegeben, wenn keine Cashflows vorhanden sind
                cashflowList.innerHTML = '<tr><td colspan="7">Keine Cashflows gefunden.</td></tr>';
            }
        }

/*
            // Funktion zum Rendern der Transaktionen
           function displayTransactions() {
                cashflowList.innerHTML = ''; // Leere die Tabelle
                transactions.forEach((transaction, index) => {

                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${transaction.date}</td>
                        <td>${transaction.type}</td>
                        <td>${transaction.category}</td>
                        <td>€${transaction.amount.toFixed(2)}</td>
                        <td>${transaction.payment_method}</td>
                        <td>${transaction.comments}</td>
                        <td>
                            <button onclick="editTransaction(${index})">Bearbeiten</button>
                            <button onclick="deleteTransaction(${index})">Löschen</button>
                        </td>
                    `;
                    cashflowList.appendChild(row);
                });
            }

            */

            // Funktion für das Bearbeiten von Transaktionen
            window.editTransaction = function (index) {
                const transaction = transactions[index];
                document.getElementById('edit-index').value = index;
                document.getElementById('edit-date').value = transaction.date;
                document.getElementById('edit-type').value = transaction.type;
                document.getElementById('edit-category').value = transaction.category;
                document.getElementById('edit-amount').value = transaction.amount;
                document.getElementById('edit-payment_method').value = transaction.payment_method;
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
                    payment_method: document.getElementById('edit-payment_method').value,
                    comment: document.getElementById('edit-comments').value,
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
                displayTransactions();
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
                        'Authorization': token // Token aus dem localStorage
                    },
                    data: JSON.stringify({ cashflow: transactionData }),
                    processData: false,
                    success: function(data) {
                        $("#serverAnswer").html(data.message);
                        alert('Transaktion erfolgreich aktualisiert!');
                        displayTransactions(); // Aktualisiere die Anzeige der Transaktionen
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
                        'Authorization': token // Token aus dem localStorage
                    },
                    success: function(data) {
                        $("#serverAnswer").html(data.message);
                        alert('Transaktion erfolgreich gelöscht!');
                        displayTransactions();
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

        cashflowList.addEventListener('click', function (event) {
            if (event.target.tagName === 'BUTTON') {
                const transactionId = event.target.dataset.id; // Transaktions-ID aus dem Datenattribut
                if (event.target.textContent === 'Bearbeiten') {
                    editTransaction(transactionId);
                } else if (event.target.textContent === 'Löschen') {
                    deleteTransaction(transactionId);
                }
            }
        });

 