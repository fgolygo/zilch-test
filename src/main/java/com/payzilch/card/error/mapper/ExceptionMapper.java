package com.payzilch.card.error.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class ExceptionMapper {
    private Map<Class<? extends Throwable>, Function<Throwable, Mono<ServerResponse>>> exceptionMapper = new HashMap<>();

    @Autowired
    private ConstraintViolationExceptionMapper constraintViolationExceptionMapper;

    @Autowired
    private ServerWebInputExceptionMapper serverWebInputExceptionMapper;

    @Autowired
    private ResponseStatusExceptionMapper responseStatusExceptionMapper;

    @Autowired
    private WebExchangeBindExceptionMapper webExchangeBindExceptionMapper;

    @Autowired
    private InternalServerErrorMapper internalServerErrorMapper;

    {
        exceptionMapper.put(WebExchangeBindException.class, (e) -> webExchangeBindExceptionMapper.map((WebExchangeBindException) e));
        exceptionMapper.put(ConstraintViolationException.class, (e) -> constraintViolationExceptionMapper.map((ConstraintViolationException) e));
        exceptionMapper.put(ServerWebInputException.class, (e) -> serverWebInputExceptionMapper.map((ServerWebInputException) e));
        exceptionMapper.put(ResponseStatusException.class, (e) -> responseStatusExceptionMapper.map());
    }

    public Mono<ServerResponse> map(Throwable error) {
        Class<? extends Throwable> specificErrorClass = error.getClass();
        Function<Throwable, Mono<ServerResponse>> errorToResponse = exceptionMapper.get(specificErrorClass);

        if (errorToResponse != null) {
            return errorToResponse.apply(error);
        } else {
            return exceptionMapper.getOrDefault(specificErrorClass.getSuperclass(), t -> internalServerErrorMapper.map(t)).apply(error);
        }
    }

}
