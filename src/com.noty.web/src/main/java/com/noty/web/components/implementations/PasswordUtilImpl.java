package com.noty.web.components.implementations;

import com.noty.web.components.PasswordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtilImpl implements PasswordUtil {
    private static final int SALT_LENGTH = 10;
    private static final int ITERATIONS = 64000;
    @Value("${noty.security.token_secret}")
    private String secret;

    private PasswordEncoder getEncoder() {
        return new Pbkdf2PasswordEncoder(
                secret,
                SALT_LENGTH,
                ITERATIONS,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512
        );
    }

    public String createHash(String password) {
        return getEncoder().encode(password);
    }

    public boolean verifyHash(String hash, String password) {
        return getEncoder().matches(
                password,
                hash
        );
    }
}
