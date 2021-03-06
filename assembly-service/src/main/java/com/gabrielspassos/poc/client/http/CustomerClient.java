package com.gabrielspassos.poc.client.http;

import com.gabrielspassos.poc.client.http.response.CustomerInfoResponse;
import com.gabrielspassos.poc.exception.CustomerNotAbleToVoteException;
import com.gabrielspassos.poc.exception.UnexpectedInternalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@Component
public class CustomerClient {

    private final String url;
    private final WebClient webClient;

    public CustomerClient(@Value("${client.customer-info.url}") String url) {
        this.url = url;
        this.webClient = WebClient.builder().build();
    }

    public Mono<CustomerInfoResponse> getCustomerInfo(String cpf) {
        String uri = createUri(cpf);
        log.info("Request GET: {}", uri);
        return webClient.get()
                .uri(uri)
                .headers(getHeaders())
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.error(new CustomerNotAbleToVoteException()))
                .onStatus(HttpStatus::isError, response -> Mono.error(new UnexpectedInternalException()))
                .bodyToMono(CustomerInfoResponse.class)
                .doOnSuccess(response -> log.info("GET {} | Response: {}", uri, response));
    }

    private String createUri(String cpf) {
        return UriComponentsBuilder
                .fromUriString(url)
                .pathSegment("users", cpf)
                .toUriString();
    }

    private Consumer<HttpHeaders> getHeaders() {
        return (httpHeaders -> {
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        });
    }
}
