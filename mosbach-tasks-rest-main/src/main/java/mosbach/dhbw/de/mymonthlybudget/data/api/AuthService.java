package mosbach.dhbw.de.mymonthlybudget.data.api;

import mosbach.dhbw.de.mymonthlybudget.model.User;


public interface AuthService {


    public String extractEmail(String token);
    public boolean isTokenExpired(String token);
    public String generateVerificationToken(User user);
    public String generateToken(User userDetails);
    public void invalidDateToken(String token);

}