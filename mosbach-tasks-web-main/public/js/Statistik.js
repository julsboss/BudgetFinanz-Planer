/*google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawVisualization);

function drawVisualization() {
  var data = google.visualization.arrayToDataTable([
    ['Monat', 'Einkommen', 'Ausgaben', 'Differenz',  'Average'],
    ['2004/05',  165,      938,         522,             614.6],
    ['2005/06',  135,      1120,        599,              682],
    ['2006/07',  157,      1167,        587,              623],
    ['2007/08',  139,      1110,        615,             609.4],
    ['2008/09',  136,      691,         629,             569.6]
  ]);

  var options = {
    title : 'Monatsbericht für Jahr',
    vAxis: {title: 'Summe in Euro €'},
    hAxis: {title: 'Monat'},
    seriesType: 'bars',
    series: {5: {type: 'line'}}
  };

  var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
  chart.draw(data, options);
}*/

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(initializeChart);

var data;
var chart;
var options;

function initializeChart() {
  data = new google.visualization.DataTable();
  data.addColumn('string', 'Monat');
  data.addColumn('number', 'Einkommen');
  data.addColumn('number', 'Ausgaben');
  data.addColumn('number', 'Differenz');
  data.addColumn('number', 'Vermögen');

  options = {
    title: 'Monatsbericht für Jahr',
    vAxis: {title: 'Summe in Euro €'},
    hAxis: {title: 'Monat'},
    seriesType: 'bars',
    series: {3: {type: 'line'}}
  };

  chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
  updateChart();
}

function addData(monat, einkommen, ausgaben, differenz, average) {
  data.addRow([monat, einkommen, ausgaben, differenz, average]);
  updateChart();
}

function updateChart() {
  chart.draw(data, options);
}

$('#yearButton').on('click', function() {
    const year = $('#year').val();
    if (year) {
        $.ajax({
        url: 'https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud/api/statistik',
        method: 'GET',
        headers: {
            'Authorization': localStorage.getItem('authToken')
        },
        contentType: 'application/json',
        data: JSON.stringify({ year: year }),

        success: function(response) {
            console.log('Statistik Report:', response);
            // Process and display the response data
            response.statistik.forEach(function(item) {
                addData(item.month, item.income_total, item.expenses_total, item.difference_summary, item.wealth);
            });
        },
        error: function(error) {
            console.error('Error fetching report:', error);
            alert('Fehler beim Abrufen des Berichts.');
        }
    });
}
});