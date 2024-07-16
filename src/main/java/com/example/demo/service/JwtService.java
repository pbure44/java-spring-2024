package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token.ttl-millis}")
    private long accessTokenTtlMillis;

    @Value("${jwt.refresh-token.ttl-millis}")
    private long refreshTokenTtlMillis;

    private Key key;

    private JwtParser jwtParser;

    @PostConstruct
    public void setUpKey() {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parser().setSigningKey(key).build();
    }

    private String generateToken(String username, long ttlMillis, Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ttlMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(UserDetails user) {
        List<String> roles = user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return generateToken(user.getUsername(), accessTokenTtlMillis, Map.of("roles", roles));
    }

    public String generateRefreshToken(UserDetails user) {
        return generateToken(user.getUsername(), refreshTokenTtlMillis, Collections.emptyMap());
    }

    public boolean isTokenValid(String token) {
        Date expireAt = extractFromToken(token, Claims::getExpiration);
        return expireAt.after(new Date());
    }

    public String extractUsername(String token) {
        return extractFromToken(token, Claims::getSubject);
    }

    public <T> T extractFromToken(String token, Function<Claims, T> extractor) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return extractor.apply(claims);
    }
}
