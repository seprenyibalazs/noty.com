package com.noty.web.controllers.api;

import com.noty.web.NotyException;
import com.noty.web.NotyValidationException;
import com.noty.web.services.security.Credentials;
import com.noty.web.services.SessionProvider;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthenticationApiController {

    private final SessionProvider sessionProvider;

    @PostMapping("/auth")
    public String Authenticate(@ModelAttribute Credentials credentials) throws NotyException {
        if (credentials == null || !credentials.isValid())
            throw new NotyValidationException("Insufficient credentials.");

        return sessionProvider.authenticate(credentials);
    }

}
