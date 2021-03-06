package com.gabrielspassos.poc.controller.v1.request;

import lombok.Data;

@Data
public class VoteRequest {

    private String choice;
    private CustomerRequest customer;

}
