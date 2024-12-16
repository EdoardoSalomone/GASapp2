package it.edoardo.gasapp2.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwUtils {

    private final String SECRET_KEY = "3qsx0+F+vqgvq/9hUOFYT7WGUhMVXHoYqwcVsn4qltsB1MbfMJUzjXVC4ewySgtLAgI4H4nTRJAcDP+A9nePfw==";
    private final long EXPIRATION_TIME = 86400000; // 1 giorno

    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRolesFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("roles", List.class);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
