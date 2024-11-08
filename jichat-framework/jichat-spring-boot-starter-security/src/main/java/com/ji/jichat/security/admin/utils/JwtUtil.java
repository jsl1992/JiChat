package com.ji.jichat.security.admin.utils;

import cn.hutool.core.util.IdUtil;
import com.ji.jichat.common.enums.ErrorCodeEnum;
import com.ji.jichat.common.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class JwtUtil {

    // 用于签名和验证JWT的密钥
    private static final String SECRET_KEY = "ABHSAZ80FhXYkINnOmmLmGeWEn0ZSiOIT26mansb8vGSS59HFjoN4B354VMAyCRC";

    // JWT过期时间，这里设置为7天
    public static final long ACCESS_TOKEN_EXPIRATION_DAY = 1000;

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(ACCESS_TOKEN_EXPIRATION_DAY);
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(ACCESS_TOKEN_EXPIRATION_DAY + 1);

    public static String createAccessToken(String subject) {
        return createJwt(subject, getAccessTokenExpirationTime());
    }


    public static Date getAccessTokenExpirationTime() {
        return new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME);
    }


    public static String createRefreshToken(String subject) {
        Date expiration = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME);
        return createJwt(subject, expiration);
    }


    public static String createJwt(String subject, Date expiration) {
        Date now = new Date();
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        // 创建JWT并设置Claims
        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key, algorithm)
                .compact();
    }


    public static Claims validateJwt(String token) {
        try {
            // 解析JWT
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            throw new ServiceException(ErrorCodeEnum.TOKEN_EXPIRES);
        } catch (Exception e) {
            // JWT验证失败
            return null;
        }
    }

    public static String validateJwtWithGetSubject(String token) {
        final Claims claims = validateJwt(token);
        if (Objects.isNull(claims)) {
            return null;
        }
        return claims.getSubject();
    }

    public static void main(String[] args) {
        // 示例：创建JWT
        String jwt = createAccessToken(IdUtil.fastSimpleUUID());
        System.out.println("Generated JWT: " + jwt);

        // 示例：验证JWT
        Claims claims = validateJwt(jwt);
        if (claims != null) {
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issued At: " + claims.getIssuedAt());
            System.out.println("Expiration: " + claims.getExpiration());
        } else {
            System.out.println("JWT validation failed.");
        }
    }
}
