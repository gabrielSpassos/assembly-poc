package com.gabrielspassos.poc.client.http;

import com.gabrielspassos.poc.client.http.response.CustomerInfoResponse;
import com.gabrielspassos.poc.exception.CustomerNotAbleToVoteException;
import com.gabrielspassos.poc.exception.UnexpectedInternalException;
import com.gabrielspassos.poc.stub.client.response.CustomerInfoResponseStub;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CustomerClientTest {

    private CustomerClient customerClient;
    private WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(6000));

    @BeforeEach
    public void setup() {
        this.customerClient = new CustomerClient("http://localhost:6000");
        this.wireMockServer.start();
    }

    @AfterEach
    public void tearDown() {
        this.wireMockServer.stop();
    }

    @Test
    public void shouldReturnCustomerInfo() {
        wireMockServer.stubFor(get(urlPathEqualTo("/users/80050098012"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(CustomerInfoResponseStub.createResponseBody())));

        CustomerInfoResponse response = customerClient.getCustomerInfo("80050098012").block();

        assertNotNull(response);
        assertEquals("UNABLE_TO_VOTE", response.getStatus());
    }

    @Test
    public void shouldThrowCustomerError() {
        wireMockServer.stubFor(get(urlPathEqualTo("/users/80050098012"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.NOT_FOUND.value())
                ));

        CustomerNotAbleToVoteException error = assertThrows(CustomerNotAbleToVoteException.class,
                () -> customerClient.getCustomerInfo("80050098012").block());

        assertEquals(HttpStatus.NOT_FOUND, error.getHttpStatus());
        assertEquals("Usuario nao habilitado para votar", error.getMessage());
        assertEquals("2", error.getCode());
    }

    @Test
    public void shouldThrowInternalError() {
        wireMockServer.stubFor(get(urlPathEqualTo("/users/80050098012"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                ));

        UnexpectedInternalException error = assertThrows(UnexpectedInternalException.class,
                () -> customerClient.getCustomerInfo("80050098012").block());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, error.getHttpStatus());
        assertEquals("Erro interno", error.getMessage());
        assertEquals("6", error.getCode());
    }

}