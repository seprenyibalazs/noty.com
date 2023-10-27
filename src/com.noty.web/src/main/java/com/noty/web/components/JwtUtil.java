package com.noty.web.components;


import com.noty.web.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        SecureRandom random = new SecureRandom(key);

        byte[] result = new byte[KEY_LEN];
        int p = Math.min(result.length, key.length);
        System.arraycopy(key, 0, result, 0, p);
        if (p < result.length) {
            byte[] padding = new byte[result.length - p];
            random.nextBytes(padding);
            System.arraycopy(padding, 0, result, p, padding.length);
        }

        return result;
    }

    public String generate(User user, Map<String, String> claims) {
        Date now = dateTime.now();
        Date expires = new Date(now.getTime() + TOKEN_TTL);
        Key secretKey = new SecretKeySpec(secretAsKey(), "HmacSHA512");

        return Jwts.builder()
                .claims(claims)
                .issuer("https://noty.com")
                .issuedAt(now)
                .expiration(expires)
                .signWith(secretKey)
                .compact();
    }

}
