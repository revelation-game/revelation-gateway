package io.github.revelationgame.gateway.openapi;

import lombok.Getter;
import lombok.Setter;
import org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
    private List<String> urlPrefixes;

    @Value("${server.port}")
    private String applicationPort;


    @GetMapping("/swagger-config.json")
    public Map<String, Object> swaggerConfig() {

        List<SwaggerUrl> urls = urlPrefixes.stream()
                // TODO externalize
                .map(prefix -> new SwaggerUrl(prefix, "http://localhost:" + applicationPort + "/" + prefix + "/custom-open-api/v3/api-docs?serverUrl=" + hostName + "/" + prefix))
                .collect(Collectors.toList());

        return Map.of("urls", urls);
    }
}
