package main.java.com.example.Pharmacy.Application.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

//@Service
@Component
public class JWTService {
//    @Value("${jwt.secret-key:secret-key}")
    private final String secretKey;
    private static final long refreshTokenValidity = 5 * 60 * 60;
    private static final long accessTokenValidity = 15;
    private static final long resetPasswordTokenValidity = 60 * 60;

    @Autowired
    public JWTService(
            @Value("${jwt.secret-key:secret-key}")
            String secretKey
    ) {
        this.secretKey = secretKey;
    }

    public String issueToken(String subject) {
        return issueToken(subject, Map.of(), accessTokenValidity);
    }

    public String issueToken(String subject, String ...scopes) {
        return issueToken(subject, Map.of("scopes", scopes), accessTokenValidity);
    }

    public String issueToken(String subject, List<String> scopes) {
        return issueToken(subject, Map.of("scopes", scopes), accessTokenValidity);
    }

    public String issueToken(
            String subject,
            Map<String, Object> claims,
            long expirationTime
    ) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("self")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(Instant.now().plus(expirationTime, ChronoUnit.MINUTES))
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String issueRefreshToken(
            String username
    ) {
        return generateToken(username, refreshTokenValidity);
    }

    // Used by refresh token, reset password
    public String generateToken(String username, long expirationTime) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(Instant.now().plus(expirationTime, ChronoUnit.MINUTES))
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String issueTokenForPasswordReset(String username) {
        return generateToken(username, resetPasswordTokenValidity);
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }
}
