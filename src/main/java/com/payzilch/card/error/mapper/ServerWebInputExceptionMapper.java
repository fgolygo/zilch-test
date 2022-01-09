package com.payzilch.card.error.mapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.payzilch.card.error.ErrorResponseLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;
import com.payzilch.card.error.ErrorCode;
import com.payzilch.card.error.Error;

@Component
class ServerWebInputExceptionMapper {

    @Autowired
    private ErrorResponseLogger errorResponseLogger;

    Mono<ServerResponse> map(ServerWebInputException exception) {
        Error body = createSimpleError(exception);
        errorResponseLogger.logError(HttpStatus.BAD_REQUEST.value(), body);
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body));
    }

    private static Error createSimpleError(ServerWebInputException exception) {
        Throwable rootCause = exception.getRootCause();

        if (rootCause instanceof JsonParseException) {
            return new Error(ErrorCode.ZILCH_005, ErrorCode.ZILCH_005.getErrorMsg());
        } else if (rootCause instanceof UnrecognizedPropertyException) {
            return new Error(ErrorCode.ZILCH_006, ErrorCode.ZILCH_006.getErrorMsg());
        } else if (rootCause instanceof MismatchedInputException && rootCause.getMessage().contains("out of START_ARRAY")) {
            return new Error(ErrorCode.ZILCH_007, ErrorCode.ZILCH_007.getErrorMsg());
        }

        return new Error(ErrorCode.ZILCH_999, ErrorCode.ZILCH_999.getErrorMsg());
    }

}
