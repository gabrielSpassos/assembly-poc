package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.client.kafka.event.CustomerEvent;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.entity.VoteEntity;

public class VoteEntityBuilder {

    public static VoteEntity build(VoteEvent voteEvent) {
        CustomerEvent customerEvent = voteEvent.getCustomer();
        CustomerEntity customer = buildCustomer(customerEvent.getId(), customerEvent.getCpf());

        return VoteEntity.builder()
                .id(null)
                .assemblyId(voteEvent.getAssemblyId())
                .voteChoice(voteEvent.getVoteChoice())
                .customer(customer)
                .build();
    }

    private static CustomerEntity buildCustomer(String id, String cpf) {
        return CustomerEntity.builder()
                .id(id)
                .cpf(cpf)
                .build();
    }

}
