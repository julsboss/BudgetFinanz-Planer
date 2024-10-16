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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.model.User;
import mosbach.dhbw.de.mymonthlybudget.data.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

public class PostgresDBUserServiceImpl implements UserService{
    @Autowired
    private AuthService authService;

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
    public static PostgresDBUserServiceImpl getUserServiceImpl() {
        if (postgresDBUserService == null)
            postgresDBUserService = new PostgresDBUserServiceImpl();
        return postgresDBUserService;
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


@Override
    public void addUser(User user) {
        Logger createUserLogger = Logger.getLogger("CreateUserLogger");
        createUserLogger.log(Level.INFO, "Start adding user with email" + user.getEmail());
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
            String insertSQL = "INSERT INTO group21Users(firstName, lastName, email, password, pat, isVerified) VALUES (?,?,?,?,?,?)";
            pstmt = connection.prepareStatement(insertSQL);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getPat());
            pstmt.setBoolean(6, user.isVerified());
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
        User user= null;
        String selectSQL = "SELECT * FROM group21Users WHERE email = ?";

        try {
            Connection connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);

            try {
                PreparedStatement pstmt = connection.prepareStatement(selectSQL);

                try {
                    pstmt.setString(1, email);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        user = new User();
                        user.setUserID(rs.getInt("userID"));
                        user.setFirstName(rs.getString("firstName"));
                        user.setLastName(rs.getString("lastName"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setPat(rs.getString("pat"));
                        user.setVerified(rs.getBoolean("isVerified"));
                        getUserLogger.log(Level.INFO, "User found: " + user.getEmail());

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

        return user;
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }


    @Override
    public User getUser(String token) {
        return getUserByEmail(authService.extractEmail(token));
    }

    public User getUserByID(int userID) {
        Logger getUserLogger = Logger.getLogger("GetUserLogger");
        getUserLogger.log(Level.INFO, "Start fetching user with ID: " + userID);
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
            String selectSQL = "SELECT * FROM group21Users WHERE user_id= ?";

            pstmt = connection.prepareStatement(selectSQL);
            pstmt.setInt(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPat(rs.getString("pat"));
                user.setVerified(rs.getBoolean("isVerified"));

                getUserLogger.log(Level.INFO, "User found: " + user.getEmail());
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

        return user;
    }

    @Override
    public String getUserPATbyID(int userID) {
        return getUserByID(userID).getPat();
    }


    public List<User> getAllUsers() {
        final Logger readUserLogger = Logger.getLogger("ReadAllUsers");
        readUserLogger.log(Level.INFO, "Start reading users");

        List<User> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        Connection connection = null;
        ResultSet rs = null;

        try {
            // Herstellung einer Verbindung zur Datenbank
            connection = DriverManager.getConnection(this.dbUrl, this.username, this.password);
            String selectSQL = "SELECT * FROM group21Users";
            pstmt = connection.prepareStatement(selectSQL);

            // Ausführen einer SQL-Abfrage, um alle Cashflows zu erhalten
            rs = pstmt.executeQuery();
            readUserLogger.log(Level.INFO, "SQL-Abfrage erfolgreich ausgeführt");
            // Iteration über das ResultSet, um Cashflow-Objekte zu erstellen
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("user_id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPat(rs.getString("pat"));
                user.setVerified(rs.getBoolean("isVerified"));

                users.add(user);
                readUserLogger.log(Level.INFO, "User hinzugefügt: " + user.getEmail());
            }


        } catch (SQLException e) {
            readUserLogger.log(Level.SEVERE, "Error fetching users: " + e.getMessage());
            e.printStackTrace();
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
            } catch (SQLException e) {
                readUserLogger.log(Level.SEVERE, "Failed to close resources: " + e.getMessage());
            }
        }

        return users;
    }


}
