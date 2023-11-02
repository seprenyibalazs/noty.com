package com.noty.web.components;


import com.noty.web.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static final int TOKEN_TTL = 24 * 60 * 60 * 1000;
    private static final int KEY_LEN = 64;

    private final DateTime dateTime;

    @Value("${noty.security.jwt_secret}")
    private String secret;

    public JwtUtil(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public byte[] secretAsKey() {
        byte[] key = secret.getBytes();
        byte[] result = new byte[KEY_LEN];
        int p = 0;
        while (p < key.length) {
            int l = Math.min(key.length, result.length - p);
            System.arraycopy(key, 0, result, p, l);
            p += l;
        }

        return result;
    }

    public String encode(User user, Map<String, String> claims) {
        Date now = dateTime.now();
        Date expires = new Date(now.getTime() + TOKEN_TTL);
        Key secretKey = new SecretKeySpec(secretAsKey(), "HmacSHA512");

        return Jwts.builder()
                .claims(claims)
                .id(String.valueOf(user.getId()))
                .subject(user.getEmail())
                .issuer("https://noty.com")
                .issuedAt(now)
                .expiration(expires)
                .signWith(secretKey)
                .compact();
    }

    public Claims decode(String jwt) {
        SecretKey secretKey = new SecretKeySpec(secretAsKey(), "HmacSHA512");

        Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKey)
                .decryptWith(secretKey)
                .build()
                .parseSignedClaims(jwt);

        return claims.getPayload();

    }


}
