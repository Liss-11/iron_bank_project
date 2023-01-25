package com.ironhack.iron_bank_project.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    //to log info in console and in the HTML if is necessary
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    // private final static String SECRET_KEY = "67566B59703373367638792F423F4528482B4D6251655468576D5A7134743777";

    @Value("${iron_bank.app.jwtSecret}")
    private String jwtSecretKey;

    @Value("${cryptocolleagues.app.jwtExpirationMs}")
    private int jwtExpirationMs;


    //The returned "subject" is our email (identifier we use for "username")
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private boolean TokenExpired(String token) {
        var expiration = extractAllClaims(token)
                .getExpiration();
        return (expiration.before(new Date()));
    }


    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(singnInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key singnInKey() {
        byte[] keyBites = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBites);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        try{
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !TokenExpired(token));
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    //The username is the email
    public String generateTokenFromUsername(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(singnInKey(), SignatureAlgorithm.ES256)
                .compact();
    }
}
