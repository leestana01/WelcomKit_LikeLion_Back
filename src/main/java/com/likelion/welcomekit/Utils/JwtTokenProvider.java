package com.likelion.welcomekit.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {
    static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public static String createToken(Long userId, String role) {
        Date now = new Date();
        long validityInMilliseconds = 10800000; // 1시간
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role",role);
        claims.put("userId",userId);
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