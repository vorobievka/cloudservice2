package com.example.cloudservice.security;

import com.example.cloudservice.model.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Data
@Service
@NoArgsConstructor
public class JwtTokenUtils {

    public String generateToken(UserEntity user) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        Instant now = Instant.now();
        Instant expirationInstant = now.plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }
}