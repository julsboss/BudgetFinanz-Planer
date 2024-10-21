package mosbach.dhbw.de.mymonthlybudget.data.impl;

import mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow;
import mosbach.dhbw.de.mymonthlybudget.data.api.MonthlyReportManager;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.model.MonthlyReport;
import mosbach.dhbw.de.mymonthlybudget.model.StatistikDTO;
import mosbach.dhbw.de.mymonthlybudget.model.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresDBMonthlyReportManagerImpl implements MonthlyReportManager{


    String databaseConnectionnUrl = "postgresql://mhartwig:BE1yEbCLMjy7r2ozFRGHZaE6jHZUx0fFadiuqgW7TtVs1k15XZVwPSBkPLZVTle6@b8b0e4b9-8325-4a3f-be73-74f20266cd1a.postgresql.eu01.onstackit.cloud:5432/stackit";
    URI dbUri;
    String username = "";
    String password = "";
    String dbUrl = "";

    static PostgresDBMonthlyReportManagerImpl postgresDBMonthlyReportManager = null;

    private PostgresDBMonthlyReportManagerImpl() {
        // basicDataSource = new BasicDataSource();
        // basicDataSource.setUrl(databaseURL);
        // basicDataSource.setUsername(username);
        // basicDataSource.setPassword(password);
        try {
            dbUri = new URI(databaseConnectionnUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        username = dbUri.getUserInfo().split(":")[0];
        password = dbUri.getUserInfo().split(":")[1];
        dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
    }
    public static PostgresDBMonthlyReportManagerImpl getPostgresDBMonthlyReportManagerImpl() {
        if (postgresDBMonthlyReportManager == null)
            postgresDBMonthlyReportManager = new PostgresDBMonthlyReportManagerImpl();
        return postgresDBMonthlyReportManager;
    }

    public void createMonthlyReportTable() {

        // Be carefull: It deletes data if table already exists.
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            stmt = connection.createStatement();
            String dropTable = "DROP TABLE IF EXISTS group21monthlyReport";
            stmt.executeUpdate(dropTable);

            String createTable = "CREATE TABLE group21monthlyReport (" +
                    "monthlyReport_id SERIAL PRIMARY KEY, " +
                    "month INT NOT NULL, " +
                    "year INT NOT NULL, " +
                    "user_id INT NOT NULL, " +
                    "total_income DECIMAL(10, 2) NOT NULL, " +
                    "total_fixed_costs DECIMAL(10, 2) NOT NULL, " +
                    "total_variable_costs DECIMAL(10, 2) NOT NULL, " +
                    "total_expenses DECIMAL(10, 2) NOT NULL, " +
                    "total_differenceSummary DECIMAL(10, 2) NOT NULL, " +
                    "FOREIGN KEY (user_id) REFERENCES group21Users(user_id)" +
                    ")";
            stmt.executeUpdate(createTable);
            stmt.close();
            connection.close();

        }  catch (SQLException e) {
            System.err.println("SQL Exception occurred while creating the table: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }
    @Override
    public void addMonthlyReport(MonthlyReportImpl report) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);

            // Check if a report already exists
            if (checkExistingReport(report.getUserID(), report.getMonth(), report.getYear())) {
                System.err.println("A report for this month and year already exists for the user.");
                return;
            }

            String insertSQL = "INSERT INTO group21monthlyReport (month, year, user_id, total_income, total_fixed_costs, " +
                    "total_variable_costs, total_expenses, total_differenceSummary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(insertSQL);
            pstmt.setInt(1, MonthConverter.monthNameToNumber(report.getMonth()));
            pstmt.setInt(2, report.getYear());
            pstmt.setInt(3, report.getUserID());
            pstmt.setDouble(4, report.getIncomeTotal());
            pstmt.setDouble(5, report.getFixedTotal());
            pstmt.setDouble(6, report.getVariableTotal());
            pstmt.setDouble(7, report.getExpensesTotal());
            pstmt.setDouble(8, report.getDifferenceSummary());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while adding the monthly report: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }

    /*@Override
    public void addMonthlyReport(MonthlyReport report) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            String insertSQL = "INSERT INTO group21monthlyReport (month, year, user_id, total_income, total_fixed_costs, " +
                    "total_variable_costs, total_expenses, total_differenceSummary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connection.prepareStatement(insertSQL);
            pstmt.setInt(1, MonthConverter.monthNameToNumber(report.getMonth()));
            pstmt.setInt(2, report.getYear());
            pstmt.setInt(3, report.getUserID());
            pstmt.setDouble(4, report.getIncomeTotal());
            pstmt.setDouble(5, report.getFixedTotal());
            pstmt.setDouble(6, report.getVariableTotal());
            pstmt.setDouble(7, report.getExpensesTotal());
            pstmt.setDouble(8, report.getDifferenceSummary());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while adding the monthly report: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }*/

    private boolean checkExistingReport(int userId, String month, int year) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int newMonth = MonthConverter.monthNameToNumber(month);
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT COUNT(*) FROM group21monthlyReport WHERE user_id = ? AND month = ? AND year = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, newMonth);
            pstmt.setInt(3, year);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while checking existing report: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }

        return false;
    }

    @Override
    public MonthlyReport getMonthlyReport(int userID, String month, Integer year) {

        if (!checkExistingReport(userID, month, year)) {
            throw new IllegalStateException("No report found for this month and year.");
        }

        // Initialize cashflow manager
        PostgresDBCashflowManagerImpl manager = PostgresDBCashflowManagerImpl.getCashflowManagerImpl();

        // Retrieve cashflows
        List<Cashflow> cashflowsIncome = manager.getCashflowByMonthAndType(userID, month, year, "Einkommen");
        List<Cashflow> cashflowsFixedCosts = manager.getCashflowByMonthAndType(userID, month, year, "Fixe Kosten");
        List<Cashflow> cashflowsVariableCosts = manager.getCashflowByMonthAndType(userID, month, year, "Variable Kosten");

        // Calculate totals
        double incomeTotal = calculateTotal(cashflowsIncome);
        double fixedTotal = calculateTotal(cashflowsFixedCosts);
        double variableTotal = calculateTotal(cashflowsVariableCosts);
        double expensesTotal = fixedTotal + variableTotal;
        double differenceSummary = incomeTotal - expensesTotal;

        // Update database with calculated totals
        updateMonthlyReport(userID, month, year, incomeTotal, fixedTotal, variableTotal, expensesTotal, differenceSummary);

        // Create and return the monthly report
        return new MonthlyReport(new MonthlyReportImpl(userID, month, year)) ;
    }

    private void updateMonthlyReport(int userId, String month, int year,
                                     double incomeTotal, double fixedTotal,
                                     double variableTotal, double expensesTotal,
                                     double differenceSummary) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            String updateSQL = "UPDATE group21monthlyReport SET total_income = ?, total_fixed_costs = ?, " +
                    "total_variable_costs = ?, total_expenses = ?, total_differenceSummary = ? " +
                    "WHERE user_id = ? AND month = ? AND year = ?";
            pstmt = connection.prepareStatement(updateSQL);
            pstmt.setDouble(1, incomeTotal);
            pstmt.setDouble(2, fixedTotal);
            pstmt.setDouble(3, variableTotal);
            pstmt.setDouble(4, expensesTotal);
            pstmt.setDouble(5, differenceSummary);
            pstmt.setInt(6, userId);
            pstmt.setInt(7, MonthConverter.monthNameToNumber(month));
            pstmt.setInt(8, year);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while updating the monthly report: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }
    private double calculateTotal(List<Cashflow> cashflows) {
        return cashflows.stream().mapToDouble(Cashflow::getAmount).sum();
    }

    @Override
    public boolean removeMonthlyReport(int monthlyReportID) {
        return false;
    }

    public List<StatistikDTO> getStatistikByYear(int userId, int year) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<StatistikDTO> statistikList = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT month, total_income, total_expenses, total_differenceSummary " +
                    "FROM group21monthlyReport WHERE user_id = ? AND year = ? ORDER BY month ASC";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, year);

            rs = pstmt.executeQuery();
            double cumulativeWealth = 0;

            while (rs.next()) {
                StatistikDTO statistik = new StatistikDTO();
                String monthName = getMonthName(rs.getInt("month"));
                double incomeTotal = rs.getDouble("total_income");
                double expensesTotal = rs.getDouble("total_expenses");
                double differenceSummary = rs.getDouble("total_differenceSummary");
                cumulativeWealth += differenceSummary;

                statistik.setMonth(monthName);
                statistik.setIncomeTotal(incomeTotal);
                statistik.setExpensesTotal(expensesTotal);
                statistik.setDifferenceSummary(differenceSummary);
                statistik.setWealth(cumulativeWealth);

                statistikList.add(statistik);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }

        return statistikList;
    }

    private String getMonthName(int monthNumber) {
        return new java.text.DateFormatSymbols().getMonths()[monthNumber - 1];
    }

}
