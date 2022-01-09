package com.payzilch.card.repository;

import com.payzilch.card.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CardRepositoryImpl implements CardRepository {

    private static final String INSERT_SQL =
            "INSERT INTO CARD (NAME, SURNAME, CARD_NUMBER, EXPIRATION_MONTH, EXPIRATION_YEAR, TYPE_ID)" +
                    " VALUES (:name, :surname, :cardNumber, :expirationMonth, :expirationYear," +
                    " (SELECT ID FROM CARD_TYPE WHERE TYPE = :type))" +
                    " RETURNING ID";

    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Mono<Long> insert(Card card) {
        return databaseClient.sql(INSERT_SQL)
                .bind("name", card.getName())
                .bind("surname", card.getSurname())
                .bind("cardNumber", card.getCardNumber())
                .bind("expirationMonth", card.getExpirationMonth())
                .bind("expirationYear", card.getExpirationYear())
                .bind("type", card.getType().name())
                .fetch()
                .one()
                .map(x -> (Long) x.get("ID"));
    }

}
