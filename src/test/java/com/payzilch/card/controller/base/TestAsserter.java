package com.payzilch.card.controller.base;

import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

public class TestAsserter {

    public static void assertJsonObjects(Object actual, String pathToJsonFile) throws Exception {
        JSONAssert.assertEquals(TestFiles.getResourceAsString(pathToJsonFile),
                TestBase.OBJECT_MAPPER.writeValueAsString(actual),
                new CustomComparator(JSONCompareMode.NON_EXTENSIBLE,
                        new Customization("id", (x, y) -> true)));
    }

    public static void assertJsonObjects(byte[] actual, String pathToJsonFile) throws JSONException {
        JSONAssert.assertEquals(TestFiles.getResourceAsString(pathToJsonFile),
                new String(actual),
                JSONCompareMode.NON_EXTENSIBLE);
    }

}
