package com.noty.web.integrationtests;


import com.noty.web.Application;
import com.noty.web.integrationtests.util.Authenticator;
import com.noty.web.integrationtests.util.SuppressingResponseErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TransactionMiddlewareFilterTests {

    private static final String TEST_USER = "test@machines.noty.com";
    private static final String TEST_PWD = "secret";

    private static String token = null;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void authenticate() {
        if (token != null)
            return;

        Authenticator authenticator = new Authenticator(port);
        token = authenticator.createAndAuthenticate(
                TEST_USER,
                TEST_PWD
        );
    }


    @Test
    public void shouldSkipFoundRequest() {
    }

    private String getUrl(String endpoint) {
        return String.format("http://localhost:%s/%s", port, endpoint);
    }

    @Test
    public void shouldProcessNewRequest() {
        // Arrange:
        HttpHeaders headers = new HttpHeaders();
        headers.add("Transaction", "trt-001");
        headers.add("Authorization", String.format("Bearer %s", token));

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("title", "transaction-create");

        HttpEntity<Object> httpEntity = new HttpEntity<>(form, headers);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new SuppressingResponseErrorHandler())
                .build();

        // Act:
        ResponseEntity<String> response = restTemplate.exchange(
                getUrl("api/list"),
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // Assert:
        assertEquals(201, response.getStatusCode().value());
        List<String> transaction = response.getHeaders().get("Transaction");
        assertNotNull(transaction);
        assertEquals(1, transaction.size());
        assertEquals("trt-001", transaction.get(0), "Invalid transaction code.");
    }

    @Test
    public void shouldAllowGetRequest() {
        // Arrange:
        HttpHeaders headers = new HttpHeaders();
        headers.add("Transaction", "trt-002");
        headers.add("Authorization", String.format("Bearer %s", token));

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new SuppressingResponseErrorHandler())
                .build();

        // Act:
        restTemplate.exchange(
                getUrl("api/user"),
                HttpMethod.GET,
                httpEntity,
                String.class
        );
        ResponseEntity<String> response = restTemplate.exchange(
                getUrl("api/user"),
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        // Assert:
        assertEquals(200, response.getStatusCode().value());
        List<String> transaction = response.getHeaders().get("Transaction");
        assertNotNull(transaction);
        assertEquals(1, transaction.size());
        assertEquals("trt-002", transaction.get(0), "Invalid transaction code.");
    }

    @Test
    public void shouldPreventRepeatedTransaction() {
        // Arrange:
        HttpHeaders headers = new HttpHeaders();
        headers.add("Transaction", "trt-004");
        headers.add("Authorization", String.format("Bearer %s", token));

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("title", "transaction-create-twice");

        HttpEntity<Object> httpEntity = new HttpEntity<>(form, headers);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(new SuppressingResponseErrorHandler())
                .build();

        // Act:
        ResponseEntity<String> response = restTemplate.exchange(
                getUrl("api/list"),
                HttpMethod.POST,
                httpEntity,
                String.class
        );
        assertEquals(201, response.getStatusCode().value());

        response = restTemplate.exchange(
                getUrl("api/list"),
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // Assert:
        assertEquals(304, response.getStatusCode().value());
        List<String> transaction = response.getHeaders().get("Transaction");
        assertNotNull(transaction);
        assertEquals(1, transaction.size());
        assertEquals("trt-004", transaction.get(0), "Invalid transaction code.");
    }

    @Test
    public void shouldSkipNotAuthenticatedRequest() {

    }

    @Test
    public void shouldSkipNonTransactionalRequest() {

    }

}
