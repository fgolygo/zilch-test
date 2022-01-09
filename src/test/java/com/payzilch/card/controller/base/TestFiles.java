package com.payzilch.card.controller.base;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TestFiles {

    public static String getResourceAsString(String filePath) {
        if (!filePath.startsWith("/")) {
            throw new RuntimeException("Path has to start with  '/'");
        }

        try {
            return IOUtils.resourceToString(filePath, UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Test resource doesn't exist with path: " + filePath);
        }
    }

}
