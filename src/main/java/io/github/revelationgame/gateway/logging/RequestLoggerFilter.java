package io.github.revelationgame.gateway.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;

@Component
@Slf4j
public class RequestLoggerFilter implements GlobalFilter, Ordered {

    private static long requestID = 0;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String remoteUrl = Optional.ofNullable(exchange.getRequest().getRemoteAddress())
                .map(InetSocketAddress::getAddress)
                .map(InetAddress::getHostAddress)
                .orElse("Failed to find removeAddress");

        long requestID = RequestLoggerFilter.getRequestID();
        long startTime = System.currentTimeMillis();

        log.info("Incoming request, ID = {}, Origin: {}, URL: {} - {}", requestID, remoteUrl, exchange.getRequest().getMethodValue(), exchange.getRequest().getURI().toString());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> logRequiredTime(requestID, startTime)));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private void logRequiredTime(long requestID, long startTime) {
        long endTime = System.currentTimeMillis();

        log.info("Request finished, ID = {}, Required Time: {}ms", requestID, endTime - startTime);
    }

    private synchronized static long getRequestID() {
        return requestID++;
    }
}