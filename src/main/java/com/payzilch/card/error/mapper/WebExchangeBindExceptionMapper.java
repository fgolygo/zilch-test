package com.payzilch.card.error.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import com.payzilch.card.error.ErrorCode;
import com.payzilch.card.error.ErrorResponseLogger;
import com.payzilch.card.error.ValidationError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WebExchangeBindExceptionMapper {

    @Autowired
    private ErrorResponseLogger errorResponseLogger;

    Mono<ServerResponse> map(WebExchangeBindException exception) {
        List<ValidationError> validationErrors = createValidationErrorList(exception.getFieldErrors());
        errorResponseLogger.logError(HttpStatus.BAD_REQUEST.value(), validationErrors);
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(validationErrors));
    }

    private static List<ValidationError> createValidationErrorList(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(WebExchangeBindExceptionMapper::toValidationError)
                .collect(Collectors.toList());
    }

    private static ValidationError toValidationError(FieldError fieldError) {
        ErrorCode errorCode = getErrorCode(fieldError);
        return new ValidationError(
                errorCode,
                fieldError.getField(),
                getErrorMessage(errorCode, fieldError)
        );
    }

    private static ErrorCode getErrorCode(FieldError fieldError) {
        String defaultMessage = fieldError.getDefaultMessage();
        if (StringUtils.isEmpty(defaultMessage)) {
            return ErrorCode.ZILCH_999;
        } else if (defaultMessage.contains("Allowed values:")) {
            return ErrorCode.ZILCH_003;
        } else {
            return formErrorCode(defaultMessage).orElse(ErrorCode.ZILCH_999);
        }
    }

    private static Optional<ErrorCode> formErrorCode(String errorCode) {
        try {
            return Optional.of(ErrorCode.valueOf(errorCode));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private static String getErrorMessage(ErrorCode errorCode, FieldError fieldError) {
        return ErrorCode.ZILCH_003 == errorCode
                ? fieldError.getDefaultMessage()
                : errorCode.getErrorMsg();
    }

}
