package com.noty.web.services.implementation;

import com.noty.web.NotyEntityNotFoundException;
import com.noty.web.NotyException;
import com.noty.web.NotyValidationException;
import com.noty.web.components.DateTime;
import com.noty.web.components.PasswordUtil;
import com.noty.web.entities.User;
import com.noty.web.model.Credentials;
import com.noty.web.model.NotyUser;
import com.noty.web.repsitories.UserRepository;
import com.noty.web.services.UserProvider;
import com.noty.web.services.security.NotyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProviderImpl implements UserProvider {

    private final DateTime dateTime;

    private final PasswordUtil passwordUtil;

    private final UserRepository userRepository;

    @Override
    public NotyUser createUser(Credentials credentials) throws NotyException {
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

        return NotyUser.fromUser(user);
    }

    @Override
    public NotyUser findByEmail(String email) throws NotyEntityNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null)
            throw new NotyEntityNotFoundException("User was not found by e-mail address.");

        return NotyUser.fromUser(user);
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
    public User fromDetails(NotyUserDetails details) {
        return details == null || details.getUser() == null
                ? null
                : findById(details.getUser().getId());
    }
}
