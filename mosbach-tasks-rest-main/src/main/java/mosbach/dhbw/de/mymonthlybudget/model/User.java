package mosbach.dhbw.de.mymonthlybudget.model;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
public class User {
    private static int userIDCounter = 1;



    @Id
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isVerified;
    public static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    public User(String firstName, String lastName, String email, String password) {
        this.userID = User.userIDCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.isVerified= false;

    }
    //Getter Setter

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }


    public void setVerified(boolean verified) {
        isVerified = verified;
    }


    public String getPassword() {
        return password;
    }
}