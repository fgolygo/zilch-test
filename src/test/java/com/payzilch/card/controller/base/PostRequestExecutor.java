package com.payzilch.card.controller.base;

import com.payzilch.card.error.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.springframework.http.HttpStatus.*;

@TestConfiguration
public class PostRequestExecutor {

    @Autowired
    private WebTestClient webClient;

    public void executePostRequestExpecting201(String endpoint, Object body) {
        executePostRequestInternal(endpoint, body, CREATED)
                .expectHeader().exists("location");
    }

    public byte[] executePostRequestExpecting400(String endpoint, Object body) {
        return executePostRequestInternal(endpoint, body, BAD_REQUEST)
                .expectBodyList(ValidationError.class)
                .returnResult()
                .getResponseBodyContent();
    }

    public byte[] executePostRequestExpecting404(String endpoint, Object body) {
        return executePostRequestInternal(endpoint, body, NOT_FOUND)
                .returnResult(ValidationError.class)
                .getResponseBodyContent();
    }

    private WebTestClient.ResponseSpec executePostRequestInternal(String endpoint, Object body, HttpStatus statusCode) {
        WebTestClient.RequestBodySpec request = webClient.post().uri(endpoint);
        request.header("Content-Type", "application/json");
        return request.body(BodyInserters.fromValue(body))
                .exchange()
                .expectStatus().isEqualTo(statusCode);
    }

}
