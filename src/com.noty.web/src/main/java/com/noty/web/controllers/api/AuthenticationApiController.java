package com.noty.web.controllers.api;

import com.noty.web.model.Credentials;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthenticationApiController {

    @PostMapping("/auth")
    public Object Authenticate(@RequestBody Credentials credentials) {
        return new HashMap<String, Object>() {{
           put("status", "nope");
        }};
    }

}
