package com.payzilch.card.error.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import com.payzilch.card.error.ErrorCode;
import com.payzilch.card.error.ErrorResponseLogger;
import com.payzilch.card.error.SimpleError;

@Component
public class InternalServerErrorMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(InternalServerErrorMapper.class);

    @Autowired
    private ErrorResponseLogger errorResponseLogger;

    Mono<ServerResponse> map(Throwable throwable) {
        LOGGER.error("Returning 500 because: {}", throwable.getMessage());
        SimpleError body = new SimpleError(ErrorCode.ZILCH_999, ErrorCode.ZILCH_999.getErrorMsg());
        errorResponseLogger.logError(HttpStatus.INTERNAL_SERVER_ERROR.value(), body);

        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body));
    }

}
