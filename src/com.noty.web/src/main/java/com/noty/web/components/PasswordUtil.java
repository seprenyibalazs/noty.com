package com.noty.web.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public interface PasswordUtil {

    String createHash(String password);

    boolean verifyHash(String hash, String password);

}
