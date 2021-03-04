package com.gabrielspassos.poc.builder.client.kafka;


import com.gabrielspassos.poc.client.kafka.request.CustomerRequest;
import com.gabrielspassos.poc.client.kafka.request.VoteRequest;
import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.entity.VoteEntity;

public class VoteRequestBuilder {

    public static VoteRequest build(String assemblyId, VoteEntity voteEntity) {
        CustomerRequest customer = buildCustomer(voteEntity.getCustomer());

        return new VoteRequest(assemblyId, voteEntity.getVoteChoice(), customer);
    }

    private static CustomerRequest buildCustomer(CustomerEntity customerEntity) {
        return new CustomerRequest(customerEntity.getCpf());
    }
}
