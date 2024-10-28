package mosbach.dhbw.de.mymonthlybudget.data.api;

import mosbach.dhbw.de.mymonthlybudget.data.impl.User;


public interface AuthService {


    public String extractUsername(String token);
    public boolean isTokenExpired(String token);
    //public String generateVerificationToken(User user);
    public String generateToken(User userDetails);
    public void invalidDateToken(String token);

}