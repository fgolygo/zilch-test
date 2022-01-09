package com.payzilch.card.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responses {

    public static <T> ResponseEntity<T> created(Long location) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, String.valueOf(location))
                .build();
    }

}
