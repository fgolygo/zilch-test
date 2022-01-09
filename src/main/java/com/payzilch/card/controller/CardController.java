package com.payzilch.card.controller;

import com.payzilch.card.model.NewCardDto;
import com.payzilch.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cards")
@Validated
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Mono<ResponseEntity<String>> createTreesAsAuthenticatedUser(@Valid @RequestBody Mono<NewCardDto> card) {
        return cardService.create(card);
    }

}
