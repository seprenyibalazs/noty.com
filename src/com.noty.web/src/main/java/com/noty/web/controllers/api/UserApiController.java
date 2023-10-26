package com.noty.web.controllers.api;

import com.noty.web.services.UserProvider;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserApiController {

    private final UserProvider userProvider;

    @PostMapping("/")
    public void createUser() {

    }

}
