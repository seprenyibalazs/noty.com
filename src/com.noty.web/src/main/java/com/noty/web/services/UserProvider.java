package com.noty.web.services;

import com.noty.web.NotyEntityNotFoundException;
import com.noty.web.NotyException;
import com.noty.web.entities.User;
import com.noty.web.services.security.Credentials;
import com.noty.web.services.security.NotyImpersonation;
import com.noty.web.services.security.NotyUserDetails;

public interface UserProvider {

    NotyImpersonation createUser(Credentials credentials) throws NotyException;

    NotyImpersonation findByEmail(String email) throws NotyEntityNotFoundException;

    User findUserByCredentials(Credentials credentials);

    User findById(long id);

    User fromDetails(NotyUserDetails details);
}
