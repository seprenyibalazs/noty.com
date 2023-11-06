package com.noty.web.integrationtests;


import com.noty.web.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(
        classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TransactionMiddlewareFilterTests {

    @LocalServerPort
    private int port;

    @Test
    public void shouldSkipFoundRequest() {

    }

    @Test
    public void shouldProcessNewRequest() {

    }

    @Test
    public void shouldSkipGetRequest() {

    }

    @Test
    public void shouldSkipNotAuthenticatedRequest() {

    }

    @Test
    public void shouldSkipNonTransactionalRequest() {

    }

}
