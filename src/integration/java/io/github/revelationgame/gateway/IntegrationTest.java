package io.github.revelationgame.gateway;

import io.github.revelationgame.gateway.test_utils.RestService;
import org.assertj.core.util.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@ContextConfiguration(classes = GatewayApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:/application-test.yml")
public class IntegrationTest {

    @Autowired
    protected RestService restService;

    protected String readAndNormalize(String name) {
        URL url = getClass().getResource(name);

        return Files.linesOf(new File(url.getFile()), StandardCharsets.UTF_8).stream()
                .map(String::trim)
                .map(s -> s.replaceAll(" ", ""))
                .collect(Collectors.joining());
    }
}
