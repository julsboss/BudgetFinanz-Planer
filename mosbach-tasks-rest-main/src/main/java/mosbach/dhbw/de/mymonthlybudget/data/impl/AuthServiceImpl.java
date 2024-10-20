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

   /* private static AuthServiceImpl authServiceImpl = null;

    private AuthServiceImpl(){};

    public static AuthServiceImpl getAuthServiceImpl(){
        if(authServiceImpl == null) authServiceImpl = new AuthServiceImpl();
        return authServiceImpl;
    }*/

    @Autowired
    private TokenBlacklistImpl tokenBlacklist;

    private String secretKey = System.getenv("JWT_Secret");

    private final long jwtExpiration = 1000 * 60 * 60 * 2;

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extratcAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(User userDetails) {

        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(HashMap<String,Object> extraClaims, User userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }


    private String buildToken(
            Map<String, Object> extraClaims,
            User userDetails,
            long expiration
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

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

    private Date extractExpiration(String token){

        return extractClaim(token, Claims::getExpiration);
    }


    private Claims extratcAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
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


    public void invalidateToken(String token){

        tokenBlacklist.blacklistToken(token);
    }





    @Override
    public void invalidDateToken(String token) {
        tokenBlacklist.blacklistToken(token);

    }


    






}
