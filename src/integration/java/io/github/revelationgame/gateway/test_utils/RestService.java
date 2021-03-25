package io.github.revelationgame.gateway.test_utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RestService {

    private final TestRestTemplate restTemplate;

    public RestService(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String get(String url) {
        return getResponse(url).getBody();
    }

    public ResponseEntity<String> getResponse(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    public ResponseEntity<String> getWitHeaders(String url, HttpHeaders httpHeaders) {
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

}
