package com.noty.web.services.implementation;

import com.noty.web.model.Credentials;
import com.noty.web.model.NotyUser;
import com.noty.web.services.UserProvider;
import org.springframework.stereotype.Service;

@Service
public class UserProviderImpl implements UserProvider {
    @Override
    public NotyUser createUser(Credentials credentials) {
        return null;
    }
}
