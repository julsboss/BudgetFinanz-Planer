package mosbach.dhbw.de.mymonthlybudget.data.impl;

import mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow;
import mosbach.dhbw.de.mymonthlybudget.data.api.CashflowManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgresDBCashflowManagerImpl implements CashflowManager {

    String databaseConnectionnUrl = "postgresql://mhartwig:BE1yEbCLMjy7r2ozFRGHZaE6jHZUx0fFadiuqgW7TtVs1k15XZVwPSBkPLZVTle6@b8b0e4b9-8325-4a3f-be73-74f20266cd1a.postgresql.eu01.onstackit.cloud:5432/stackit";
    URI dbUri;
    String username = "";
    String password = "";
    String dbUrl = "";
    // BasicDataSource basicDataSource;

    // Singleton
    static PostgresDBCashflowManagerImpl postgresDBCashflowManager = null;
    private PostgresDBCashflowManagerImpl() {
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
    public static PostgresDBCashflowManagerImpl getCashflowManagerImpl() {
        if (postgresDBCashflowManager == null)
            postgresDBCashflowManager = new PostgresDBCashflowManagerImpl();
        return postgresDBCashflowManager;
    }

    public void createCashflowTable() {

        // Be carefull: It deletes data if table already exists.
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            stmt = connection.createStatement();
            String dropTable = "DROP TABLE IF EXISTS group21cashflows";
            stmt.executeUpdate(dropTable);

            String createTable = "CREATE TABLE group21cashflows (" +
                    "cashflow_id SERIAL PRIMARY KEY, " +
                    "type VARCHAR(50) NOT NULL, " +
                    "category VARCHAR(100) NOT NULL, " +
                    "amount DECIMAL(10, 2) NOT NULL, " +
                    "date DATE NOT NULL, " +
                    "payment_method VARCHAR(50) NOT NULL, " +
                    "repetition VARCHAR(50), " +
                    "comment TEXT, " +
                    "user_id INT NOT NULL, " +
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
    public List<Cashflow> getCashflowsByUser(String token) {
        return List.of();
    }


    @Override
    public void addCashflow(Cashflow cashflow, int userID) {
         final Logger createCashflowLogger = Logger.getLogger("CreateCashflowLogger");
         createCashflowLogger.log(Level.INFO, "Start creating cashflow of type " + cashflow.getType());

         Connection connection = null;
         PreparedStatement pstmt = null;

         try {
             connection = DriverManager.getConnection(dbUrl, username, password);

             String insertSQL = "INSERT INTO group21cashflows (type, category, amount, date, payment_method, repetition, comment, user_id) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
             pstmt = connection.prepareStatement(insertSQL);
             pstmt.setString(1, cashflow.getType());
             pstmt.setString(2, cashflow.getCategory());
             pstmt.setDouble(3, cashflow.getAmount());
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             try {
                 java.util.Date utilDate = sdf.parse(cashflow.getDate());
                 Date sqlDate = new Date(utilDate.getTime());
                 pstmt.setDate(4, sqlDate);
             } catch (ParseException e) {
                 e.printStackTrace();
                 // Handle the parse exception (e.g., log it or notify the user)
             }
            // pstmt.setString(4, cashflow.getDate());
             pstmt.setString(5, cashflow.getPaymentMethod());
             pstmt.setString(6, cashflow.getRepetition());
             pstmt.setString(7, cashflow.getComment());
             pstmt.setInt(8, userID);

             pstmt.executeUpdate();

         } catch (SQLException e ) {
             e.printStackTrace();
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
    public List<Cashflow> getAllCashflows() {
        final Logger readCashflowLogger = Logger.getLogger("ReadCashflowLogger");
        readCashflowLogger.log(Level.INFO, "Start reading cashflows");

        List<Cashflow> cashflows = new ArrayList<>();
        Statement stmt = null;
        Connection connection = null;

        try {
            // Herstellung einer Verbindung zur Datenbank
            connection = DriverManager.getConnection(dbUrl, username, password);
            stmt = connection.createStatement();

            // Ausführen einer SQL-Abfrage, um alle Cashflows zu erhalten
            ResultSet rs = stmt.executeQuery("SELECT * FROM group21cashflows");
            readCashflowLogger.log(Level.INFO, "SQL-Abfrage erfolgreich ausgeführt");
            // Iteration über das ResultSet, um Cashflow-Objekte zu erstellen
            while (rs.next()) {
                cashflows.add(
                        new CashflowImpl(
                                rs.getInt("cashflow_id"),  // Lesen der ID
                                rs.getString("type"),  // Lesen des Typs (Einkommen oder Ausgabe)
                                rs.getString("category"),  // Lesen der Kategorie
                                rs.getDouble("amount"),  // Lesen des Betrags
                                rs.getDate("date").toString(),  // Lesen des Datums
                                rs.getString("payment_method"),  // Lesen der Zahlungsmethode
                                rs.getString("repetition"),  // Lesen der Wiederholung
                                rs.getString("comment"),
                                rs.getInt("user_id")// Lesen der Anmerkungen

                ));
            }
            readCashflowLogger.log(Level.INFO, "Cashflow hinzugefügt: " + rs.getInt("cashflow_id"));
            // Schließen des Statements und der Verbindung
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();  // Fehlerbehandlung bei SQL-Ausnahmen
        }

        return cashflows;  // Rückgabe der Liste von Cashflow-Objekten
    }

    @Override
    public boolean removeCashflow(int cashflowID) {
        final Logger removeCashflowLogger = Logger.getLogger("RemoveCashflowLogger");
        removeCashflowLogger.log(Level.INFO, "Attempting to remove cashflow with ID: " + cashflowID);

        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean isRemoved = false;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);

            String deleteSQL = "DELETE FROM group21cashflows WHERE cashflow_id = ?";
            pstmt = connection.prepareStatement(deleteSQL);
            pstmt.setInt(1, cashflowID);

            int affectedRows = pstmt.executeUpdate();
            isRemoved = (affectedRows > 0);

            if (isRemoved) {
                removeCashflowLogger.log(Level.INFO, "Cashflow with ID: " + cashflowID + " was successfully removed.");
            } else {
                removeCashflowLogger.log(Level.WARNING, "No cashflow found with ID: " + cashflowID);
            }

        } catch (SQLException e) {
            removeCashflowLogger.log(Level.SEVERE, "SQL Exception occurred while removing cashflow: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                removeCashflowLogger.log(Level.SEVERE, "Failed to close resources: " + e.getMessage());
            }
        }

        return isRemoved;

    }
    public List<Cashflow> getCashflowByMonthAndType(int userID, String month, int year, String type) {
        final Logger logger = Logger.getLogger("GetCashflowByMonthLogger");
        logger.log(Level.INFO, "Fetching cashflows for user_ID: " + userID + ", month: " + month + "/" + year + ", type: " + type);
        int monthNumber = MonthConverter.monthNameToNumber(month); // TODO: muss man schauen, ob die klasse wirklich klappt
        List<Cashflow> cashflows = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);

            String sql = "SELECT * FROM group21cashflows WHERE user_id = ? AND ((repetition = 'monthly') OR (MONTH(date) = ? AND YEAR(date) = ?)) AND type = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, monthNumber);
            pstmt.setInt(3, year);
            pstmt.setString(4, type);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cashflow cashflow = new CashflowImpl(
                        rs.getInt("cashflow_id"),
                        rs.getString("type"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getDate("date").toString(),
                        rs.getString("payment_method"),
                        rs.getString("repetition"),
                        rs.getString("comment")
                );
                cashflows.add(cashflow);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Failed to close resources: " + e.getMessage());
            }
        }

        return cashflows;
    }
    public List<Cashflow> getCashflowByMonth(int userID, int month, int year) {
        final Logger logger = Logger.getLogger("GetCashflowByMonthLogger");
        logger.log(Level.INFO, "Fetching cashflows for user ID: " + userID + " for month: " + month + "/" + year);

        List<Cashflow> cashflows = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);

            String sql = "SELECT * FROM group21cashflows WHERE user_id = ? AND MONTH(date) = ? AND YEAR(date) = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, month);
            pstmt.setInt(3, year);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cashflow cashflow = new CashflowImpl(
                        rs.getInt("cashflow_id"),
                        rs.getString("type"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getDate("date").toString(),
                        rs.getString("payment_method"),
                        rs.getString("repetition"),
                        rs.getString("comment")
                );
                cashflows.add(cashflow);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Failed to close resources: " + e.getMessage());
            }
        }

        return cashflows;
    }
    public List<Cashflow> getCashflowsByUserID(int userId) {
        final Logger logger = Logger.getLogger("GetCashflowsByUserIdLogger");
        logger.log(Level.INFO, "Fetching cashflows for user ID: " + userId);

        List<Cashflow> cashflows = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            String sql = "SELECT * FROM group21cashflows WHERE user_id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cashflow cashflow = new CashflowImpl(
                        rs.getInt("cashflow_id"),
                        rs.getString("type"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getDate("date").toString(),
                        rs.getString("payment_method"),
                        rs.getString("repetition"),
                        rs.getString("comment")
                );
                cashflows.add(cashflow);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Failed to close resources: " + e.getMessage());
            }
        }

        return cashflows;
    }

    @Override
    public void updateCashflow(Cashflow cashflow, int userId) {
        final Logger logger = Logger.getLogger("PutCashflowsByCashflowIdLogger");
        logger.log(Level.INFO, "Updating cashflows for user ID: " + userId);
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);

            String updateSQL = "UPDATE group21cashflows SET type = ?, category = ?, amount = ?, date = ?, " +
                    "payment_method = ?, repetition = ?, comment = ? WHERE cashflow_id = ? AND user_id = ?";
            pstmt = connection.prepareStatement(updateSQL);
            pstmt.setString(1, cashflow.getType());
            pstmt.setString(2, cashflow.getCategory());
            pstmt.setDouble(3, cashflow.getAmount());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(cashflow.getDate());
            Date sqlDate = new Date(utilDate.getTime());
            pstmt.setDate(4, sqlDate);

            pstmt.setString(5, cashflow.getPaymentMethod());
            pstmt.setString(6, cashflow.getRepetition());
            pstmt.setString(7, cashflow.getComment());
            pstmt.setInt(8, cashflow.getCashflowID());
            pstmt.setInt(9, userId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Cashflow updated successfully.");
            } else {
                System.out.println("No cashflow found with the specified ID and user ID.");
            }

        } catch (SQLException | ParseException e) {
            System.err.println("Exception occurred while updating the cashflow: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }
    public Cashflow getCashflowById(int cashflowID) {
        final Logger logger = Logger.getLogger("GetCashflowByIdLogger");
        logger.log(Level.INFO, "Fetching cashflow with ID: " + cashflowID);

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cashflow cashflow = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);

            String query = "SELECT * FROM group21cashflows WHERE cashflow_id = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, cashflowID);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                cashflow = new CashflowImpl(
                        rs.getInt("cashflow_id"),
                        rs.getString("type"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getDate("date").toString(),
                        rs.getString("payment_method"),
                        rs.getString("repetition"),
                        rs.getString("comment"),
                        rs.getInt("user_id")
                );
                logger.log(Level.INFO, "Cashflow found: " + cashflowID);
            } else {
                logger.log(Level.WARNING, "No cashflow found with ID: " + cashflowID);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred while fetching cashflow: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Failed to close resources: " + e.getMessage());
            }
        }

        return cashflow;
    }

}
