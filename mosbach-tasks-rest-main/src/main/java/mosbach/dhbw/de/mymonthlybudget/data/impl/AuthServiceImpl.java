package mosbach.dhbw.de.mymonthlybudget.data.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import mosbach.dhbw.de.mymonthlybudget.data.api.AuthService;
import mosbach.dhbw.de.mymonthlybudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private TokenBlacklistImpl tokenBlacklist;

    private String secretKey = System.getenv("JWT_Secret");

    private final long jwtExpiration = 1000 * 60 * 60 * 2;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean isTokenExpired(String token) {
        try{
            if(tokenBlacklist.isTokenBlacklisted(token)){
                return true;
            }
            return extractExpiration(token).before(new Date());
        }
        catch (JwtException e){
            return true;
        }
    }
    public void invalidateToken(String token){
        tokenBlacklist.blacklistToken(token);
    }

    @Override
    public String generateVerificationToken(User user) {
        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 *60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public void invalidDateToken(String token) {
        tokenBlacklist.blacklistToken(token);

    }

    private String generateToken(HashMap<String,Object> extraClaims, User userDetails) {
        return buildToken(extraClaims, userDetails);
    }
    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extratcAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extratcAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private String buildToken(
            Map<String, Object> extraClaims,
            User userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7200000L))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }


}
