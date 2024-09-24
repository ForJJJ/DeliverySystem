package com.forj.auth.infrastructure.jwt;

import com.forj.auth.domain.model.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret.key}")
    private String key;

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        byte[] bytes = Base64.getDecoder().decode(key);
        secretKey = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Long userId, UserRole role) {
        Date now = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(userId.toString())
                        .claim("role", role.name())
                        .expiration(new Date(now.getTime() + expiration))
                        .issuedAt(now)
                        .signWith(secretKey)
                        .compact();
    }

}
