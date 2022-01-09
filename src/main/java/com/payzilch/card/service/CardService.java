package com.payzilch.card.service;

import com.payzilch.card.model.NewCardDto;
import com.payzilch.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.payzilch.card.utils.Responses;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Mono<ResponseEntity<String>> create(Mono<NewCardDto> cardDto) {
        return cardDto.map(NewCardGenerator::generate)
                .flatMap(cardRepository::insert)
                .map(Responses::created);
    }

}
