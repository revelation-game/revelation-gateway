package io.github.revelationgame.gateway.security;

import io.github.revelationgame.gateway.IntegrationTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class SecurityConfigIT extends IntegrationTest {

    @Value("${revelation.security.username}")
    private String adminUsername;

    @Value("${revelation.security.password}")
    private String adminPassword;

    @ParameterizedTest
    @ValueSource(strings = {"/some/path/sec/something/", "/some/sec/something/", "/some/something/something/sec", "/sec/some/path"})
    void authorizationInterceptorWithSec_NoSec(String url) {
        ResponseEntity<String> actual = restService.getResponse(url);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/game/path/sec/something/", "/game/sec/something/", "/game/something/something/sec"})
    void authorizationInterceptorWithSec_Success(String url) {
        feignClientMock.getJsonStub(url.replace("/game", ""), "string");

        HttpHeaders headers = restService.createHeaders(adminUsername, adminPassword);

        ResponseEntity<String> actual = restService.getWitHeaders(url, headers);
        assertThat(actual.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(actual.getBody()).isEqualTo("string");
    }

    @ParameterizedTest
    @ValueSource(strings = {"/game/path/sec/something/", "/game/sec/something/", "/game/something/something/sec", "/sec/some/path"})
    void authorizationInterceptorWithSec_InvalidPW(String url) {
        HttpHeaders headers = restService.createHeaders(adminUsername, "wrongPw");

        ResponseEntity<String> actual = restService.getWitHeaders(url, headers);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
