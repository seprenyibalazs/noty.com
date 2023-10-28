package com.noty.web.controllers.api;

import com.noty.web.NotyEntityNotFoundException;
import com.noty.web.NotyException;
import com.noty.web.model.Credentials;
import com.noty.web.model.NotyUser;
import com.noty.web.services.UserProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserProvider userProvider;

    @PostMapping(value = "/")
    public NotyUser createUser(@RequestBody Credentials credentials) throws NotyException {
        return userProvider.createUser(credentials);

    }

    @GetMapping(value = "/")
    public NotyUser getUser(@AuthenticationPrincipal UserDetails user) throws NotyEntityNotFoundException {
        return userProvider.findByEmail(user.getUsername());
    }

}
