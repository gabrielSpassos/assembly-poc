package com.gabrielspassos.poc.builder.client.kafka;


import com.gabrielspassos.poc.client.kafka.event.CustomerEvent;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.entity.VoteEntity;

public class VoteEventBuilder {

    public static VoteEvent build(String assemblyId, VoteEntity voteEntity) {
        CustomerEvent customer = buildCustomer(voteEntity.getCustomer());

        return new VoteEvent(assemblyId, voteEntity.getVoteChoice(), customer);
    }

    private static CustomerEvent buildCustomer(CustomerEntity customerEntity) {
        return new CustomerEvent(customerEntity.getId(), customerEntity.getCpf());
    }
}
