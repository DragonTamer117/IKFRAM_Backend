package bikeShop.shopUser;


import bikeShop.shopUser.shopUserModels.ShopUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String secretKey = "appeltaart";

    public String extractShopUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String shopUserEmail) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, shopUserEmail);
    }

    public String createToken(Map<String, Object> claims, String subject) {
        int halfAnHour = 1000 * 60 * 30;
        System.out.println(secretKey);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + halfAnHour))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Boolean validateToken(String token, String shopUserEmail) {
        final String tokenShopUserEmail = extractShopUserEmail(token);
        return (tokenShopUserEmail.equals(shopUserEmail) && !isTokenExpired(token));
    }
}

