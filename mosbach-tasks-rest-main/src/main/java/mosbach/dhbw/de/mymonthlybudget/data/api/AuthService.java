package mosbach.dhbw.de.mymonthlybudget.data.api;

import mosbach.dhbw.de.mymonthlybudget.model.User;
import org.springframework.stereotype.Service;


public interface AuthService {


    public String extractUsername(String token);
    public boolean isTokenExpired(String token);
    public String generateVerificationToken(User user);
    public String generateToken(User userDetails);
    public void invalidDateToken(String token);

}