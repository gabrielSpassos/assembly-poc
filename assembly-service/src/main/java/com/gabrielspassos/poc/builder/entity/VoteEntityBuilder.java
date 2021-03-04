package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.controller.v1.request.VoteRequest;
import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.entity.VoteEntity;

public class VoteEntityBuilder {

    public static VoteEntity build(VoteRequest voteRequest) {
        CustomerEntity customer = buildCustomer(voteRequest.getCustomer());

        return VoteEntity.builder()
                .voteChoice(voteRequest.getChoice())
                .customer(customer)
                .build();
    }

    private static CustomerEntity buildCustomer(CustomerRequest customerRequest) {
        return CustomerEntity.builder()
                .cpf(customerRequest.getCpf())
                .build();
    }

}
