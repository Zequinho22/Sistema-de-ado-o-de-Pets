package com.adote.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private final Key key = Keys.hmacShaKeyFor(System.getenv().getOrDefault("JWT_SECRET","change-me-please-change-me-please-32bytes").getBytes());
    private final long expirationMs = 1000L*60*60*6; // 6h

    public String generate(String userId, String email){
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
