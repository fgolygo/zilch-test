package com.payzilch.card.controller;

import com.payzilch.card.controller.base.TestAsserter;
import com.payzilch.card.controller.base.TestBase;
import com.payzilch.card.controller.base.TestFiles;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static com.payzilch.card.controller.base.TestAsserter.assertJsonObjects;

public class InvalidRequestControllerTest extends TestBase {
    private static final String CREATE_CARD_ENDPOINT = "/api/cards";

    @Test
    void shouldReturnNotFoundWhenNotExistingEndpoint() throws JSONException {
        //when
        byte[] response = postRequestExecutor.executePostRequestExpecting404("/api/notExistingEndpoint", "{}");

        //then
        TestAsserter.assertJsonObjects(response, "/validation/generic/output/not_existing_endpoint.json");
    }

    @Test
    void shouldReturnBadRequestWhenInvalidJsonStructure() throws JSONException {
        //given
        String badInput = TestFiles.getResourceAsString("/validation/generic/input/invalid_json_structure.json");

        //when
        byte[] actual = postRequestExecutor.executePostRequestExpecting400(CREATE_CARD_ENDPOINT, badInput);

        //then
        TestAsserter.assertJsonObjects(actual, "/validation/generic/output/invalid_json_structure.json");
    }

    @Test
    void shouldReturnBadRequestWhenExtraPropertiesInJsonBodyStructure() throws JSONException {
        //given
        String badInput = TestFiles.getResourceAsString("/validation/generic/input/extra_properties_in_body.json");

        //when
        byte[] actual = postRequestExecutor.executePostRequestExpecting400(CREATE_CARD_ENDPOINT, badInput);

        //then
        TestAsserter.assertJsonObjects(actual, "/validation/generic/output/extra_json_properties.json");
    }

    @Test
    void shouldReturnBadRequestWhenArrayInsteadOfObjectInInput() throws JSONException {
        //given
        String badInput = TestFiles.getResourceAsString("/validation/generic/input/array_instead_of_object.json");

        //when
        byte[] actual = postRequestExecutor.executePostRequestExpecting400(CREATE_CARD_ENDPOINT, badInput);

        //then
        TestAsserter.assertJsonObjects(actual, "/validation/generic/output/array_instead_of_object.json");
    }

}
