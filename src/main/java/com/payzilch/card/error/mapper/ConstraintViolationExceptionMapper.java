package com.payzilch.card.error.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import com.payzilch.card.error.ErrorCode;
import com.payzilch.card.error.ErrorResponseLogger;
import com.payzilch.card.error.ValidationError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class ConstraintViolationExceptionMapper {

    @Autowired
    private ErrorResponseLogger errorResponseLogger;

    Mono<ServerResponse> map(ConstraintViolationException exception) {
        List<ValidationError> validationErrors = createValidationErrorList(exception);
        errorResponseLogger.logError(HttpStatus.BAD_REQUEST.value(), validationErrors);
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(validationErrors));
    }

    private static List<ValidationError> createValidationErrorList(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .map(ConstraintViolationExceptionMapper::toValidationError)
                .collect(Collectors.toList());
    }

    private static ValidationError toValidationError(ConstraintViolation<?> constraintViolation) {
        ErrorCode errorCode = getErrorCode(constraintViolation.getMessage());
        return new ValidationError(
                errorCode,
                StringUtils.substringAfterLast(constraintViolation.getPropertyPath().toString(), "."),
                errorCode.getErrorMsg()
        );
    }

    private static ErrorCode getErrorCode(String violationMessage) {
        return formErrorCode(violationMessage).orElse(ErrorCode.ZILCH_999);

    }

    private static Optional<ErrorCode> formErrorCode(String errorCode) {
        try {
            return Optional.of(ErrorCode.valueOf(errorCode));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
