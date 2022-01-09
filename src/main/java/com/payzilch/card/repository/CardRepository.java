package com.payzilch.card.repository;

import com.payzilch.card.model.Card;
import reactor.core.publisher.Mono;

public interface CardRepository {
    Mono<Long> insert(Card card);
}
