package com.noty.web.controllers.api;

import com.noty.web.NotyException;
import com.noty.web.NotyValidationException;
import com.noty.web.model.Credentials;
import com.noty.web.services.SessionProvider;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthenticationApiController {

    private final SessionProvider sessionProvider;

    @PostMapping("/auth")
    public Object Authenticate(@RequestBody Credentials credentials) throws NotyException {
        if (credentials == null || !credentials.isValid())
            throw new NotyValidationException("Insufficient credentials.");

        return sessionProvider.authenticate(credentials);
    }

}
