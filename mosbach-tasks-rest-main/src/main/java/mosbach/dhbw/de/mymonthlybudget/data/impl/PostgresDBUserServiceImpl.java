//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package mosbach.dhbw.de.mymonthlybudget.data.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.model.User;

public class PostgresDBUserServiceImpl implements UserService {
    String databaseConnectionnUrl = "postgresql://mhartwig:BE1yEbCLMjy7r2ozFRGHZaE6jHZUx0fFadiuqgW7TtVs1k15XZVwPSBkPLZVTle6@b8b0e4b9-8325-4a3f-be73-74f20266cd1a.postgresql.eu01.onstackit.cloud:5432/stackit";
    URI dbUri;
    String username = "";
    String password = "";
    String dbUrl = "";
    static PostgresDBUserServiceImpl postgresDBUserService = null;

    private PostgresDBUserServiceImpl() {
        try {
            this.dbUri = new URI(this.databaseConnectionnUrl);
        } catch (URISyntaxException var2) {
            URISyntaxException e = var2;
            throw new RuntimeException(e);
        }

        this.username = this.dbUri.getUserInfo().split(":")[0];
        this.password = this.dbUri.getUserInfo().split(":")[1];
        String var10001 = this.dbUri.getHost();
        this.dbUrl = "jdbc:postgresql://" + var10001 + ":" + this.dbUri.getPort() + this.dbUri.getPath() + "?sslmode=require";
    }

    public void createUserTable() {
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
            stmt = connection.createStatement();
            String dropTable = "DROP TABLE IF EXISTS group21Users";
            stmt.executeUpdate(dropTable);
            String createTable = "CREATE TABLE group21Users (user_id SERIAL PRIMARY KEY,firstName VARCHAR(50) NOT NULL,lastName VARCHAR(50) NOT NULL,email VARCHAR(100) NOT NULL UNIQUE,password VARCHAR(100) NOT NULL);";
            stmt.executeUpdate(createTable);
            stmt.close();
            connection.close();
        } catch (SQLException var15) {
            SQLException e = var15;
            System.err.println("SQL Exception occurred while creating the table: " + e.getMessage());
        } catch (Exception var16) {
            Exception e = var16;
            System.err.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException var14) {
                SQLException e = var14;
                System.err.println("Failed to close resources: " + e.getMessage());
            }

        }

    }

    public void addUser(User user) {
        Logger createUserLogger = Logger.getLogger("CreateUserLogger");
        createUserLogger.log(Level.INFO, "Start adding user with email" + user.getEmail());
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
            String insertSQL = "INSERT INTO users(firstName, lastName, email, password) VALUES (?,?,?,?)";
            pstmt = connection.prepareStatement(insertSQL);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.executeUpdate();
            createUserLogger.log(Level.INFO, "User added successfully: " + user.getEmail());
        } catch (SQLException var14) {
            SQLException e = var14;
            createUserLogger.log(Level.SEVERE, "Error addind user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException var13) {
                SQLException e = var13;
                System.err.println("Failed to close resources: " + e.getMessage());
            }

        }

    }

    public User getUserByEmail(String email) {
        Logger getUserLogger = Logger.getLogger("GetUserLogger");
        getUserLogger.log(Level.INFO, "Start fetching user with email: " + email);
        User foundUser = null;
        String selectSQL = "SELECT user_id, firstName, lastName, email, password FROM users WHERE email = ?";

        try {
            Connection connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);

            try {
                PreparedStatement pstmt = connection.prepareStatement(selectSQL);

                try {
                    pstmt.setString(1, email);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        foundUser = new User(rs.getInt("user_id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getString("password"), rs.getString("pat"));
                        getUserLogger.log(Level.INFO, "User found: " + foundUser.getEmail());
                    } else {
                        getUserLogger.log(Level.WARNING, "User not found with email: " + email);
                    }
                } catch (Throwable var11) {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        } catch (Throwable var10) {
                            var11.addSuppressed(var10);
                        }
                    }

                    throw var11;
                }

                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Throwable var12) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var9) {
                        var12.addSuppressed(var9);
                    }
                }

                throw var12;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var13) {
            SQLException e = var13;
            getUserLogger.log(Level.SEVERE, "Error fetching user: " + e.getMessage());
            e.printStackTrace();
        }

        return foundUser;
    }

    public User getUser(String token) {
        Logger getUserLogger = Logger.getLogger("GetUserLogger");
        getUserLogger.log(Level.INFO, "Start fetching user with token: " + token);
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User foundUser = null;

        try {
            connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
            String selectSQL = "SELECT firstName, lastName, email, password FROM users WHERE token = ?";
            pstmt = connection.prepareStatement(selectSQL);
            pstmt.setString(1, token);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                foundUser = new User(rs.getInt("user_id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getString("password"), rs.getString("pat"));
                getUserLogger.log(Level.INFO, "User found: " + foundUser.getEmail());
            } else {
                getUserLogger.log(Level.WARNING, "User not found with token: " + token);
            }
        } catch (SQLException var16) {
            SQLException e = var16;
            getUserLogger.log(Level.SEVERE, "Error fetching user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException var15) {
                SQLException e = var15;
                System.err.println("Failed to close resources: " + e.getMessage());
            }

        }

        return foundUser;
    }

    public User getUserByID(int userID) {
        Logger getUserLogger = Logger.getLogger("GetUserLogger");
        getUserLogger.log(Level.INFO, "Start fetching user with ID: " + userID);
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User foundUser = null;

        try {
            connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
            String selectSQL = "SELECT firstName, lastName, email, password FROM users WHERE user_id = ?";
            pstmt = connection.prepareStatement(selectSQL);
            pstmt.setInt(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                foundUser = new User(rs.getInt("user_id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getString("password"), rs.getString("pat"));
                getUserLogger.log(Level.INFO, "User found: " + foundUser.getEmail());
            } else {
                getUserLogger.log(Level.WARNING, "User not found with ID: " + userID);
            }
        } catch (SQLException var16) {
            SQLException e = var16;
            getUserLogger.log(Level.SEVERE, "Error fetching user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pstmt != null) {
                    pstmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException var15) {
                SQLException e = var15;
                System.err.println("Failed to close resources: " + e.getMessage());
            }

        }

        return foundUser;
    }
}
