package io.github.revelationgame.gateway.openapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl;

@ExtendWith(MockitoExtension.class)
class SwaggerUiConfigTest {

    @InjectMocks
    private SwaggerUiConfig swaggerUiConfig;

    @Test
    void swaggerConfig() {
        swaggerUiConfig.setUrlPrefixes(List.of("/url1", "/url2"));
        swaggerUiConfig.setHostName("hostname");
        swaggerUiConfig.setPublicPrefix("/p");

        Map<String, List<SwaggerUrl>> actual = swaggerUiConfig.swaggerConfig();

        SwaggerUrl expectedUrl1 = new SwaggerUrl("/url1", "hostname/url1/p/custom-open-api/v3/api-docs?serverUrl=hostname/url1");
        SwaggerUrl expectedUrl2 = new SwaggerUrl("/url2", "hostname/url2/p/custom-open-api/v3/api-docs?serverUrl=hostname/url2");
        assertThat(actual.keySet()).containsExactly("urls");
        assertThat(actual.get("urls")).usingRecursiveComparison().isEqualTo(List.of(expectedUrl1, expectedUrl2));
    }
}