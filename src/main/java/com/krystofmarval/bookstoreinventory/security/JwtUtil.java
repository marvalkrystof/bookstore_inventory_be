/**
 * Utility class for handling JWT (JSON Web Token) operations.
 * <p>
 * This class provides methods for generating, extracting, and validating JWT tokens
 * to manage authentication and authorization in the application.
 * </p>
 *
 * <b>Key Features:</b>
 * <ul>
 *   <li>Generates JWT tokens with a specified expiration time.</li>
 *   <li>Extracts the username from a token.</li>
 *   <li>Validates tokens by checking expiration and user identity.</li>
 * </ul>
 *
 * @author Kryštof Marval
 * @since 1.0
 */
package com.krystofmarval.bookstoreinventory.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    /**
     * Secret key used for signing JWT tokens.
     * <p>
     * This value is injected from application properties.
     * </p>
     */
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Generates a JWT token for the given user.
     * <p>
     * The token includes the username as the subject and has a validity of 10 hours.
     * </p>
     *
     * @param userDetails The user details for whom the token is generated.
     * @return A signed JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Extracts the username from a given JWT token.
     *
     * @param token The JWT token.
     * @return The username contained within the token.
     */
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validates a JWT token by checking the username and expiration.
     *
     * @param token       The JWT token to validate.
     * @param userDetails The user details to compare against the token's subject.
     * @return {@code true} if the token is valid; {@code false} otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks whether a given JWT token is expired.
     *
     * @param token The JWT token.
     * @return {@code true} if the token has expired; {@code false} otherwise.
     */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getExpiration();
        return expiration.before(new Date());
    }
}
