package com.gathadi.ajay.ecommerce.security.jwt;

import com.gathadi.ajay.ecommerce.security.services.UserDetailsImplementation;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HexFormat;

@Component
@Slf4j
public class JwtUtils {

    @Value(value = "${ecommerce.app.jwtSecret}")
    private String jwtSecret;

    @Value(value = "${ecommerce.app.jwtExpirationMs}")
    private long jwtExpirationMs;

    /**
     * Parses the jwtSecret String and generates the bytes[] array which CPU can understand
     *
     * @return byte array
     */
    private Key key() {
        return Keys.hmacShaKeyFor(HexFormat.of().parseHex(jwtSecret));
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        log.info("[getJwtFromHeader]");
        // 1. Read the 'Authorization' header from the request
        String header = request.getHeader("Authorization");
        log.debug("Authorization Header: {}", header);

        // 2. Check for non-null and "Bearer " prefix
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public String generateJwtToken(Authentication authentication) {
        log.info("[generateJwtToken]");
        // 1: Extract the authenticated user's details (Spring stores the logged-in user inside the 'principal'
        UserDetailsImplementation userPrincipal = (UserDetailsImplementation) authentication.getPrincipal();

        //2. Calculate the timestamps.
        Date now = new Date();  // Returns the current time in Milliseconds
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // 2: Build the token using JJWT's builder pattern.
        return Jwts.builder()
                // 2.1: Set the Subject (The "Who" - usually the username)
                .subject(userPrincipal.getUsername())

                // 2.2: Set the Issue Date (When was this printed?)
                .issuedAt(now)

                // 2.3: Set the Expiration Date (Issue Date + expiration in milliseconds)
                .expiration(expiryDate)

                // 2.4: Cryptographically sign it using key() method, which return the byte[]
                .signWith(key())

                // 2.5: Squash it down into the final "header.payload.signature" String
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        log.info("[validateJwtToken]");
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException malformedJwtException) {
            log.error("Invalid JWT Token: {}", malformedJwtException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("JWT Token is expired: {}", expiredJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            log.error("JWT token is unsupported: {}", unsupportedJwtException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error("JWT Claims string is empty: {}", illegalArgumentException.getMessage());
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        log.info("[getUserNameFromJwtToken]");
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
