//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package mosbach.dhbw.de.mymonthlybudget.model;

import jakarta.persistence.Id;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class User {
    public boolean checkPassword;
    @Id
    private int userID;
    private String pat;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isVerified;
    public static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public User(int userID, String firstName, String lastName, String email, String password, String pat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.isVerified = false;
        this.pat = "";
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return this.isVerified;
    }

    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPat() {
        return this.pat;
    }

    public void setPat(String pat) {
        this.pat = pat;
    }

    public boolean checkToken() {
        return !this.pat.isEmpty();
    }

    public boolean checkPassword(String password) {
        return passwordEncoder.matches(password, this.password);
    }
}
