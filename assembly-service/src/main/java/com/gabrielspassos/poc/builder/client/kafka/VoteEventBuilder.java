package com.gabrielspassos.poc.builder.client.kafka;


import com.gabrielspassos.poc.client.kafka.event.CustomerEvent;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.VoteDTO;

public class VoteEventBuilder {

    public static VoteEvent build(String assemblyId, VoteDTO voteDTO) {
        CustomerEvent customer = buildCustomer(voteDTO.getCustomer());

        return new VoteEvent(assemblyId, voteDTO.getVoteChoice(), customer);
    }

    private static CustomerEvent buildCustomer(CustomerDTO customerDTO) {
        return new CustomerEvent(customerDTO.getId(), customerDTO.getCpf());
    }
}
