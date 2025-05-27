package com.kevin.hotelbooking.service;

import com.kevin.hotelbooking.entities.User;
import com.kevin.hotelbooking.entities.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user) {

        return Jwts.builder()
                    .subject(user.getId().toString())
                    .claim("email", user.getEmail())
                    .claim("roles", user.getIsAdmin())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .compact();
    }

    public boolean validateToken(String token) {
        try{

            var claims = getClaims(token);

            return claims.getExpiration().after(new Date());

        } catch (JwtException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    public UserRole getUserRole(String token) {
        return UserRole.valueOf(getClaims(token).get("roles", String.class));
    }

}
