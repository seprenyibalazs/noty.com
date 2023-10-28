package com.noty.web.controllers.api;

import com.noty.web.NotyEntityNotFoundException;
import com.noty.web.NotyException;
import com.noty.web.controllers.api.model.response.NotyUserResponse;
import com.noty.web.services.UserProvider;
import com.noty.web.services.security.Credentials;
import com.noty.web.services.security.NotyImpersonation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserProvider userProvider;

    @PostMapping(value = "/")
    public NotyUserResponse createUser(@RequestBody Credentials credentials) throws NotyException {
        NotyImpersonation impersonation = userProvider.createUser(credentials);
        return NotyUserResponse.fromImpersonation(impersonation);

    }

    @GetMapping(value = "/")
    public NotyUserResponse getUser(@AuthenticationPrincipal UserDetails user) throws NotyEntityNotFoundException {
        NotyImpersonation impersonation = userProvider.findByEmail(user.getUsername());
        return NotyUserResponse.fromImpersonation(impersonation);
    }

}
