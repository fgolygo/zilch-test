package com.payzilch.card.controller;

import com.payzilch.card.controller.base.TestBase;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.payzilch.card.controller.base.TestFiles;
import com.payzilch.card.model.Card;
import com.payzilch.card.model.NewCardDto;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static com.payzilch.card.controller.base.TestAsserter.assertJsonObjects;

public class CardControllerTest extends TestBase {
    private static final String CREATE_CARD_ENDPOINT = "/api/cards";

    @Test
    void shouldReturn201WhenCardCreated() throws Exception {
        //given
        String input = TestFiles.getResourceAsString("/validation/card/input/valid_card.json");
        NewCardDto newCardDto = OBJECT_MAPPER.readValue(input, NewCardDto.class);

        //when
        postRequestExecutor.executePostRequestExpecting201(CREATE_CARD_ENDPOINT, input);

        //then
        Card actual = dbExecutor.selectLastInsertedCard();
        assertThat(actual.getName()).isEqualTo(newCardDto.getName());
        assertThat(actual.getSurname()).isEqualTo(newCardDto.getSurname());
        assertThat(actual.getType()).isEqualTo(newCardDto.getType());
        assertThat(actual.getExpirationMonth()).isEqualTo(LocalDate.now().getMonthValue());
        assertThat(actual.getExpirationYear()).isEqualTo(LocalDate.now().plusYears(5).getYear());
        String cardNumber = actual.getCardNumber();
        assertThat(cardNumber).hasSize(16);
        assertThat(cardNumber).startsWith("5575");
    }

    @MethodSource("cardCreationBadRequest")
    @ParameterizedTest
    void shouldReturn400WhenInvalidCardCreationRequest(String inputPath, String expectedPath) throws JSONException {
        //given
        String badInput = TestFiles.getResourceAsString(inputPath);

        //when
        byte[] actual = postRequestExecutor.executePostRequestExpecting400(CREATE_CARD_ENDPOINT, badInput);

        //then
        assertJsonObjects(actual, expectedPath);
    }

    private static Stream<Arguments> cardCreationBadRequest() {
        Arguments missingRequiredData = Arguments.of("/validation/card/input/missing_required_data.json", "/validation/card/output/missing_required_data.json");
        Arguments invalidData = Arguments.of("/validation/card/input/invalid_data.json", "/validation/card/output/invalid_data.json");
        return Stream.of(missingRequiredData, invalidData);
    }

}
