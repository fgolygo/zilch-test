package com.payzilch.card.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorResponseLogger.class);

    @Autowired
    private ObjectMapper objectMapper;

    public void logError(int statusCode, Object response) {
        try {
            LOGGER.info("Response returned: status={}, payload={}", statusCode, objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize error response", e);
        }
    }

}
