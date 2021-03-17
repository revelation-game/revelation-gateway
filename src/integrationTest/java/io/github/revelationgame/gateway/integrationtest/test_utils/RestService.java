package io.github.revelationgame.gateway.integrationtest.test_utils;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class RestService {

    private final TestRestTemplate restTemplate;

    public RestService(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String get(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
    }

}
