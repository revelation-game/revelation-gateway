package io.github.revelationgame.gateway.integrationtest.openapi;


import io.github.revelationgame.gateway.integrationtest.IntegrationTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SwaggerUiConfigIT extends IntegrationTest {

    @Test
    void swaggerConfig() {
        String actual = restService.get("/swagger-config.json");

        String expected = readAndNormalize("swaggerConfig_expected.json");

        assertThat(actual).isEqualTo(expected);
    }
}