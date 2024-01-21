package com.likelion.welcomekit.Utils;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenProvider {

/*
    // 고정된 문자열 키로, 바이트 배열을 사용하여 SecretKey 생성
    String secretString = "my-secret-key";
    byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);
    SecretKey key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
*/

//    유동적인 키로, 매번 초기화되는 SecretKey 생성
    SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String createToken(String username, String role) {
        Date now = new Date();
        // 1시간 (예시)
        long validityInMilliseconds = 3600000;
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claim("role", role)
                .issuedAt(now)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // 토큰 검증 실패 처리 (예: 로그 남기기, 예외 던지기 등)
            return false;
        }
    }
}