package YakDaBang.Yakdabang.utility;

import YakDaBang.Yakdabang.domain.dto.response.JwtTokenDto;
import YakDaBang.Yakdabang.domain.dto.type.ERole;
import YakDaBang.Yakdabang.global.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for handling JSON Web Tokens (JWTs).
 * This class provides methods to generate access and refresh tokens with specific claims,
 * as well as to validate tokens using a secret key. Tokens are signed with the HS512
 * algorithm and support user ID and role claims.
 *
 * <p>Configuration properties:
 * <ul>
 *   <li><code>jwt.secret-key</code>: Base64-encoded secret key for signing JWTs.</li>
 *   <li><code>jwt.access-token-expire-period</code>: Expiration period (in seconds) for access tokens.</li>
 *   <li><code>jwt.refresh-token-expire-period</code>: Expiration period (in seconds) for refresh tokens.</li>
 * </ul>
 *
 * Dependencies:
 * This class uses io.jsonwebtoken (jjwt) for token generation and validation.
 */
@Component
public class JwtUtil implements InitializingBean {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token-expire-period}")
    private Integer accessTokenExpirePeriod;
    @Value("${jwt.refresh-token-expire-period}")
    @Getter
    private Integer refreshTokenExpirePeriod;

    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenDto generateTokens(Long id, ERole role) {
        return new JwtTokenDto(
                generateToken(id, role, accessTokenExpirePeriod * 1000),
                generateToken(id, null, refreshTokenExpirePeriod * 1000));
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(Long id, ERole role, Integer expirePeriod) {
        Claims claims = Jwts.claims();
        claims.put(Constants.USER_ID_CLAIM_NAME, id);
        if (role != null)
            claims.put(Constants.USER_ROLE, role);

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirePeriod))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


}
