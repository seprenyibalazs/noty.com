package com.noty.web.services.implementation;

import com.noty.web.NotyAuthorizationException;
import com.noty.web.components.JwtUtil;
import com.noty.web.components.PasswordUtil;
import com.noty.web.entities.User;
import com.noty.web.services.security.Credentials;
import com.noty.web.services.SessionProvider;
import com.noty.web.services.UserProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class SessionProviderImpl implements SessionProvider {

    private final UserProvider userProvider;

    private final PasswordUtil passwordUtil;

    private final JwtUtil jwtUtil;

    public String authenticate(Credentials credentials) throws NotyAuthorizationException {
        User user = userProvider.findUserByCredentials(credentials);
        if (user == null)
            throw new NotyAuthorizationException("User not found.");

        if (!passwordUtil.verifyHash(user.getToken(), credentials.getPassword()))
            throw new NotyAuthorizationException("Invalid password.");

        return jwtUtil.encode(user, new HashMap<>());
    }

}
