package com.noty.web.services;

import com.noty.web.NotyAuthorizationException;
import com.noty.web.services.security.Credentials;

public interface SessionProvider {

    String authenticate(Credentials credentials) throws NotyAuthorizationException;
}
