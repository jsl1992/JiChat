package com.ji.jichat.security.admin.utils;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class JwtUtil {

    // 用于签名和验证JWT的密钥
    private static final String SECRET_KEY = "ABHSAZ80FhXYkINnOmmLmGeWEn0ZSiOIT26mansb8vGSS59HFjoN4B354VMAyCRC";

    // JWT过期时间，这里设置为7天
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(7);
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(8);

    public static String generateAccessToken(String subject) {
        return generateJwt(subject, getAccessTokenExpirationTime());
    }


    public static Date getAccessTokenExpirationTime() {
        return new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME);
    }


    public static String generateRefreshToken(String subject) {
        Date expiration = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME);
        return generateJwt(subject, expiration);
    }


    public static String generateJwt(String subject, Date expiration) {
        Date now = new Date();
        // 创建JWT并设置Claims
        String jwt = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return jwt;
    }


    public static Claims validateJwt(String token) {
        try {
            // 解析JWT
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
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
        String jwt = generateAccessToken(IdUtil.fastSimpleUUID());
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
