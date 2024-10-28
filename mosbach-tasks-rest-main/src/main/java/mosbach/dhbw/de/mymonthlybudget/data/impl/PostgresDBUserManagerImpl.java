package mosbach.dhbw.de.mymonthlybudget.data.impl;

import mosbach.dhbw.de.mymonthlybudget.data.api.AuthService;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PostgresDBUserManagerImpl implements UserManager {

    String databaseConnectionUrl = "postgresql://mhartwig:BE1yEbCLMjy7r2ozFRGHZaE6jHZUx0fFadiuqgW7TtVs1k15XZVwPSBkPLZVTle6@b8b0e4b9-8325-4a3f-be73-74f20266cd1a.postgresql.eu01.onstackit.cloud:5432/stackit";
    URI dbUri;
    String username = "";
    String password = "";
    String dbUrl = "";

    @Autowired
    private AuthService authService;

    static PostgresDBUserManagerImpl postgresDBUserManager = null;
    private PostgresDBUserManagerImpl() {
        // basicDataSource = new BasicDataSource();
        // basicDataSource.setUrl(databaseURL);
        // basicDataSource.setUsername(username);
        // basicDataSource.setPassword(password);
        try {
            dbUri = new URI(databaseConnectionUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        username = dbUri.getUserInfo().split(":")[0];
        password = dbUri.getUserInfo().split(":")[1];
        dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
    }
    public static PostgresDBUserManagerImpl getPostgresDBUserManagerImpl() {
        if (postgresDBUserManager == null)
            postgresDBUserManager = new PostgresDBUserManagerImpl();
        return postgresDBUserManager;
    }

    public void createUserTable() {
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            stmt = connection.createStatement();
            String dropTable = "DROP TABLE IF EXISTS group21Users";
            stmt.executeUpdate(dropTable);

            String createTable = "CREATE TABLE group21Users (" +
                    "user_id SERIAL PRIMARY KEY, " +
                    "firstName VARCHAR(50) NOT NULL, " +
                    "lastName VARCHAR(50) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL UNIQUE, " +
                    "passwort VARCHAR(100) NOT NULL " +
                    ")";
            stmt.executeUpdate(createTable);
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while creating the table: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }


    public boolean updateUser(User user) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean isUpdated = false;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            String updateSQL = "UPDATE group21Users SET firstName = ?, lastName = ?, passwort = ? WHERE email = ?";
            pstmt = connection.prepareStatement(updateSQL);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());

            int affectedRows = pstmt.executeUpdate();
            isUpdated = (affectedRows > 0);

        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while updating user: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }

        return isUpdated;
    }



    @Override
    public boolean deleteUser(int userID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        boolean isDeleted = false;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            String deleteSQL = "DELETE FROM group21Users WHERE user_id = ?";
            pstmt = connection.prepareStatement(deleteSQL);
            pstmt.setInt(1, userID);

            int affectedRows = pstmt.executeUpdate();
            isDeleted = (affectedRows > 0);

        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while deleting user: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }

        return isDeleted;
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            stmt = connection.createStatement();
            String query = "SELECT * FROM group21Users";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("passwort")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while retrieving users: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }

        return users;
    }

    @Override
    public void addUser(User user) {
        final Logger createUserLogger = Logger.getLogger("CreateUserLogger");
        createUserLogger.log(Level.INFO, "Start creating user: " + user.getEmail());

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);

            String insertSQL = "INSERT INTO group21Users (firstName, lastName, email, passwort) VALUES (?, ?, ?, ?)";
            pstmt = connection.prepareStatement(insertSQL);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());


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


    public User getUserByEmail(String email) {
        final Logger logger = Logger.getLogger("UserLogger");
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT * FROM group21Users WHERE email = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);

            logger.log(Level.INFO, "Executing query to find user by email: " + email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                        user.setUserID(rs.getInt("user_id"));
                        user.setFirstName(rs.getString("firstName"));
                        user.setLastName(rs.getString("lastName"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("passwort"));
                logger.log(Level.INFO, "User found: " + user.getEmail());
            } else {
                logger.log(Level.WARNING, "No user found with email: " + email);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred while retrieving user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Failed to close resources: " + e.getMessage());
            }
        }

        return user;
    }

    @Override
    public User getUser(String token) {
        return getUserByEmail(authService.extractUsername(token));
    }

    @Override
    public User getUserByID(int userID) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
            String query = "SELECT * FROM group21Users WHERE user_id = ?";
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, userID);

            rs = pstmt.executeQuery();


                if (rs.next()) {
                    user = new User();
                    user.setUserID(rs.getInt("user_id"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("passwort"));
                ;
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while retrieving user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }

        return user;
    }
}
