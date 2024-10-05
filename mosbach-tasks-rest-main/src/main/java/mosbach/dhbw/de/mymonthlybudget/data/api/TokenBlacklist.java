package mosbach.dhbw.de.mymonthlybudget.data.api;

public interface TokenBlacklist {
    public void blacklistToken(String token);
    public boolean isTokenBlacklisted(String token);
}
