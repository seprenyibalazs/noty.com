package com.noty.web.services.implementation;

import com.noty.web.NotyEntityNotFoundException;
import com.noty.web.NotyException;
import com.noty.web.NotyValidationException;
import com.noty.web.components.DateTime;
import com.noty.web.components.PasswordUtil;
import com.noty.web.entities.User;
import com.noty.web.repsitories.UserRepository;
import com.noty.web.services.UserProvider;
import com.noty.web.services.security.Credentials;
import com.noty.web.services.security.NotyImpersonation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProviderImpl implements UserProvider {

    private final DateTime dateTime;

    private final PasswordUtil passwordUtil;

    private final UserRepository userRepository;

    @Override
    public NotyImpersonation createUser(Credentials credentials) throws NotyException {
        if (credentials == null)
            throw new IllegalArgumentException("Credentials must not be null.");

        if (!credentials.isValid())
            throw new NotyValidationException("Invalid credentials supplied.");

        String token = passwordUtil.createHash(credentials.getPassword());
        User user = new User(
                credentials.getEmail(),
                token,
                dateTime.now()
        );

        userRepository.save(user);

        return NotyImpersonation.fromUser(user);
    }

    @Override
    public NotyImpersonation findByEmail(String email) throws NotyEntityNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new NotyEntityNotFoundException("User was not found by e-mail address.");

        return NotyImpersonation.fromUser(user);
    }

    @Override
    public User findUserByCredentials(Credentials credentials) {
        return userRepository.findUserByEmail(credentials.getEmail());

    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User fromDetails(NotyImpersonation impersonation) {
        return impersonation == null
                ? null
                : findById(impersonation.getId());
    }
}
