package com.noty.web.integrationtests.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Authenticator {

    private final int port;

    public Authenticator(
            int port
    ) {

        this.port = port;
    }

    private <T> ResponseEntity<T> request(
            String endpoint,
            HttpMethod method,
            Map<String, String> data,
            Class<T> responseClass
    ) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        for (String key : data.keySet())
            form.add(key, data.get(key));

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(
                form,
                new HttpHeaders()
        );

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                String.format("http://localhost:%s/%s", port, endpoint),
                method,
                httpEntity,
                responseClass
        );
    }

    private String authenticateUser(String email, String password) {
        Map<String, String> data = new HashMap<>() {{
            put("email", email);
            put("password", password);
        }};

        ResponseEntity<String> response = request("api/auth", HttpMethod.POST, data, String.class);
        assertEquals(200, response.getStatusCode().value());

        return response.getBody();
    }

    private void createUser(String email, String password) {
        Map<String, String> data = new HashMap<>() {{
           put("email", email);
           put("password", password);
        }};

        ResponseEntity<String> response = request("api/user", HttpMethod.POST, data, String.class);
        assertEquals(200, response.getStatusCode().value());
    }

    public String createAndAuthenticate(String email, String password) {
        createUser(email, password);
        return authenticateUser(email, password);
    }

}
