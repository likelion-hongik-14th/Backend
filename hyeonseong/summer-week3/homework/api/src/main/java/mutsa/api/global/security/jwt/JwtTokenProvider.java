package mutsa.api.global.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long expirationTime;

    public JwtTokenProvider(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") long expirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationTime;
    }

    // 유저 ID(providerId)를 받아서 JWT 토큰을 만들어주는 메서드
    public String createAccessToken(String providerId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(providerId)           // 토큰의 주인 (카카오 고유 ID)
                .claim("role", "ROLE_USER")    // 권한 정보
                .issuedAt(now)                 // 발행 시간
                .expiration(expiryDate)        // 만료 시간
                .signWith(secretKey)           // 비밀키로 서명 (위변조 방지)
                .compact();
    }
}