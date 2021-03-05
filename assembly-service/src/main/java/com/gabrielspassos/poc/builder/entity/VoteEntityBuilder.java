package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.client.kafka.event.CustomerEvent;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
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

    public static VoteEntity build(VoteEvent voteEvent) {
        CustomerEntity customer = buildCustomer(voteEvent.getCustomer());

        return VoteEntity.builder()
                .voteChoice(voteEvent.getVoteChoice())
                .customer(customer)
                .build();
    }

    private static CustomerEntity buildCustomer(CustomerRequest customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .cpf(customer.getCpf())
                .build();
    }

    private static CustomerEntity buildCustomer(CustomerEvent customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .cpf(customer.getCpf())
                .build();
    }

}
