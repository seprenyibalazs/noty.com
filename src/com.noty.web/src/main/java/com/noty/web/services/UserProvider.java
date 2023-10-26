package com.noty.web.services;

import com.noty.web.NotyException;
import com.noty.web.model.Credentials;
import com.noty.web.model.NotyUser;

public interface UserProvider {

    public NotyUser createUser(Credentials credentials) throws NotyException;

}
