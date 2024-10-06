package mosbach.dhbw.de.mymonthlybudget.data.api;

public interface VerificationService {

    public void sendVerificationEmail(String to, String verificationLink);
}
