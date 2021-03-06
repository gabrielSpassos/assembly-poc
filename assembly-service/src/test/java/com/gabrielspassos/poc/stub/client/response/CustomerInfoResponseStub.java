package com.gabrielspassos.poc.stub.client.response;

import com.gabrielspassos.poc.client.http.response.CustomerInfoResponse;

public class CustomerInfoResponseStub {

    public static CustomerInfoResponse create(String status) {
        CustomerInfoResponse customerInfoResponse = new CustomerInfoResponse();
        customerInfoResponse.setStatus(status);
        return customerInfoResponse;
    }

    public static String createResponseBody() {
        return "{\n" +
                "\"status\": \"UNABLE_TO_VOTE\"\n" +
                "}";
    }
}
