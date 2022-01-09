package com.payzilch.card.controller.base;

import com.payzilch.card.model.Card;
import com.payzilch.card.repository.mapping.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;

public class DbExecutor {
    private static final CardMapper cardMapper = new CardMapper();
    private static final String SELECT_LAST_INSERTED_CARD =
            "select * from card join card_type on type_id = card_type.id where card.id = (select max(id) from card)";

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    public Card selectLastInsertedCard() {
        return databaseClient.sql(SELECT_LAST_INSERTED_CARD)
                .fetch()
                .one()
                .map(cardMapper::map)
                .block();
    }

}
