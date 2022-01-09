package com.payzilch.card.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FlywayConfig {
    private final Environment env;

    public FlywayConfig(final Environment env) {
        this.env = env;
    }

    /* This is because there is no support for Flyway R2DBC yet
    * https://github.com/flyway/flyway/issues/2502
    */
    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway(Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(
                        env.getRequiredProperty("spring.flyway.url"),
                        env.getRequiredProperty("spring.flyway.user"),
                        env.getRequiredProperty("spring.flyway.password"))
        );
    }
}
