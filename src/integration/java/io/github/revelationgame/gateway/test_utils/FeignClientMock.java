package io.github.revelationgame.gateway.test_utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;


@Component
@Lazy
public class FeignClientMock {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WireMockServer wireMockServer;

    public void getJsonStub(String url, String body) {
        MappingBuilder stubMapping = WireMock.get(WireMock.urlEqualTo(url)).willReturn(WireMock.aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(body));

        wireMockServer.addStubMapping(stubMapping.build());
    }

}
