package com.payzilch.card.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(value = {
        PostRequestExecutor.class,
        DbExecutor.class
})
@ActiveProfiles("test")
public class TestBase {
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withPassword("test")
            .withUsername("test");

    @Autowired
    protected PostRequestExecutor postRequestExecutor;

    @Autowired
    protected DbExecutor dbExecutor;

    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    private static void replaceDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> postgreSQLContainer.getJdbcUrl().replace("jdbc", "r2dbc"));
        registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername);
        registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);

        registry.add("spring.flyway.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.flyway.user", postgreSQLContainer::getUsername);
        registry.add("spring.flyway.password", postgreSQLContainer::getPassword);
    }

}
