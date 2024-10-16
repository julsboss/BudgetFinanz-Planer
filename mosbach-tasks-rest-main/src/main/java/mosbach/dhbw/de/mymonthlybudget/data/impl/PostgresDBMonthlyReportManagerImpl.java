package mosbach.dhbw.de.mymonthlybudget.data.impl;

import mosbach.dhbw.de.mymonthlybudget.data.api.MonthlyReportManager;
import mosbach.dhbw.de.mymonthlybudget.model.MonthlyReport;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

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
                    // Ensure the users table exists and has a user_id column
                   // "FOREIGN KEY (user_id) REFERENCES Group21Users(user_id)" +
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
    }

    @Override
    public MonthlyReport getMonthlyReport(String token, String month, Integer year) {
        return null;
    }

    @Override
    public boolean removeMonthlyReport(int monthlyReportID) {
        return false;
    }


}
