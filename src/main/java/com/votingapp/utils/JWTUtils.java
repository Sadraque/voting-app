package com.votingapp.utils;

import com.votingapp.configuration.JWTConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log
@RequiredArgsConstructor
public class JWTUtils {

    private final JWTConfig jwtConfig;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(jwtConfig.getSignatureAlgorithm(), jwtConfig.getSecret().getBytes())
                .compact();
    }

    public Boolean isValidToken(String token) {
        Claims claims = getClaims(token);

        if (claims != null) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            return username != null && now.before(expirationDate);
        }

        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception exception) {
            log.severe(String.format("Token it is not valid. %s", exception.getMessage()));
            return null;
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);

        if (claims != null) {
           return claims.getSubject();
        }

        return null;
    }
}
