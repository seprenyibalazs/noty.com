package com.noty.web.services;

import com.noty.web.NotyEntityNotFoundException;
import com.noty.web.NotyException;
import com.noty.web.entities.User;
import com.noty.web.model.Credentials;
import com.noty.web.model.NotyUser;

public interface UserProvider {

    NotyUser createUser(Credentials credentials) throws NotyException;

    NotyUser findByEmail(String email) throws NotyEntityNotFoundException;

    User findUserByCredentials(Credentials credentials);
}
