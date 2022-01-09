package com.payzilch.card.service;

import com.payzilch.card.model.Card;
import com.payzilch.card.model.NewCardDto;

import java.security.SecureRandom;
import java.time.LocalDate;

class NewCardGenerator {
    private static final String ZILCH_CARD_NUMBER_PREFIX = "5575";
    private static final SecureRandom RANDOM = new SecureRandom();

    static Card generate(NewCardDto newCardDto) {
        Card card = new Card();
        card.setName(newCardDto.getName());
        card.setSurname(newCardDto.getSurname());
        card.setCardNumber(generateCardNumber());
        card.setExpirationMonth(LocalDate.now().getMonthValue());
        card.setExpirationYear(LocalDate.now().plusYears(5).getYear());
        card.setType(newCardDto.getType());
        return card;
    }

    private static String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder(ZILCH_CARD_NUMBER_PREFIX);
        RANDOM.ints(12, 0, 9)
                .forEach(cardNumber::append);
        return cardNumber.toString();
    }

}
