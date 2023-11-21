package nvt.st.springjwtauthentication.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import nvt.st.springjwtauthentication.security.CustomUserDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    //nvt.jwt.*  : defined in application.properties

    // string JWT
    @Value("${nvt.jwt.secret}")
    private String JWT_SECRET;
    // time expire
    @Value("${nvt.jwt.expiration}")
    private int JWT_EXPIRATION;

    // creation jwt from information user
    public String generateToken(CustomUserDetail customUserDetail){
        // expiry date is now date plus JWT_EXPIRATION
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+JWT_EXPIRATION);

        // creation string JWT(JSON WEB TOKEN) from information username(email) of user (note: using info unique to create JWT)
        // return string JWT unique cho each user
        return Jwts.builder()
                .setSubject(customUserDetail.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                // (giải thuật mã hóa , Key)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();

    }

    // get information user from JWT (JSON WEB TOKEN)
    public String getUserNameFromJwt(String token){
        // get all claims of token (ex: Subject, Issue, Expire .... : JWT include multiple claims)
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        // return username
        return claims.getSubject();
    }

    // Validate information of string JWT
    public boolean validateToken(String authToken) {
        try{
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(authToken);
            return true;
        }catch (MalformedJwtException m){
            log.error("Invalid JWT token!");
        }catch (ExpiredJwtException ex){
            log.error("Expired JWT token!");
        }catch (UnsupportedJwtException un){
            log.error("Unsupported JWT token!");
        }catch (IllegalArgumentException i){
            log.error("JWT claims string is empty!");
        }
        return false;
    }

}
