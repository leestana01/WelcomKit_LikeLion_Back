package com.likelion.welcomekit.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {
    static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public static String createToken(String userName, String role) {
        Date now = new Date();
        long validityInMilliseconds = 3600000; // 1시간
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        Map<String, String> claims = new HashMap<>();
        claims.put("role",role);
        claims.put("userName",userName);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public static Claims extractClaims(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public static boolean isExpired(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
                .getPayload().getExpiration().before(new Date());
    }
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // 토큰 검증 실패 처리 (예: 로그 남기기, 예외 던지기 등)
            return false;
        }
    }
}