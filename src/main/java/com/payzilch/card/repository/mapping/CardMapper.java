package com.payzilch.card.repository.mapping;

import com.payzilch.card.model.Card;
import org.springframework.stereotype.Component;
import com.payzilch.card.model.CardType;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class CardMapper {
    private static final String NOT_FOUND_COLUMN_ERROR = "Could not find column with name: %s";

    public Card map(Map<String, Object> dbResult) {
        Card card = new Card();
        card.setId(getValue(dbResult, "ID", Long.class));
        card.setName(getValue(dbResult, "NAME", String.class));
        card.setSurname(getValue(dbResult, "SURNAME", String.class));
        card.setCardNumber(getValue(dbResult, "CARD_NUMBER", String.class));
        card.setExpirationMonth(getValue(dbResult, "EXPIRATION_MONTH", Integer::parseInt));
        card.setExpirationYear(getValue(dbResult, "EXPIRATION_YEAR", Integer::parseInt));
        card.setType(getValue(dbResult, "TYPE", CardType::valueOf));
        return card;
    }

    private static <T> T getValue(Map<String, Object> dbResult, String columnName, Class<T> type) {
        return Optional.ofNullable(dbResult.get(columnName))
                .map(type::cast)
                .orElseThrow(() -> new RuntimeException(String.format(NOT_FOUND_COLUMN_ERROR, columnName)));
    }

    private static <T> T getValue(Map<String, Object> dbResult, String columnName, Function<String, T> mapper) {
        return Optional.ofNullable(dbResult.get(columnName))
                .map(String.class::cast)
                .map(mapper)
                .orElseThrow(() -> new RuntimeException(String.format(NOT_FOUND_COLUMN_ERROR, columnName)));
    }

}
