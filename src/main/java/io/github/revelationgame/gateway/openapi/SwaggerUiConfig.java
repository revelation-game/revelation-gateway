package io.github.revelationgame.gateway.openapi;

import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@ConfigurationProperties(prefix = "revelation.custom-swagger")
public class SwaggerUiConfig {

    @Getter
    @Setter
    private String hostName;

    @Getter
    @Setter
    private String publicPrefix;

    @Getter
    @Setter
    private List<String> urlPrefixes;

    @GetMapping("/swagger-config.json")
    public Map<String, List<SwaggerUrl>> swaggerConfig() {

        List<SwaggerUrl> urls = urlPrefixes.stream()
                .map(prefix -> new SwaggerUrl(prefix, getApiUrl(prefix)))
                .collect(Collectors.toList());

        return Map.of("urls", urls);
    }

    private String getApiUrl(String prefix) {
        String path = hostName + prefix;
        return path + publicPrefix + "/custom-open-api/v3/api-docs?serverUrl=" + path;
    }
}
