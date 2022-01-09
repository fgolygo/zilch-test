package com.payzilch.card.error.mapper;

import com.payzilch.card.error.ErrorCode;
import com.payzilch.card.error.ErrorResponseLogger;
import com.payzilch.card.error.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
class ResponseStatusExceptionMapper {

    @Autowired
    private ErrorResponseLogger errorResponseLogger;

    Mono<ServerResponse> map() {
        Error body = new Error(ErrorCode.ZILCH_004, ErrorCode.ZILCH_004.getErrorMsg());
        errorResponseLogger.logError(HttpStatus.NOT_FOUND.value(), body);
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body));
    }

}
