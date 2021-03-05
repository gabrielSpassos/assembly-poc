package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.client.kafka.event.CustomerEvent;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.controller.v1.request.VoteRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.entity.VoteEntity;

public class VoteDTOBuilder {

    public static VoteDTO build(VoteEntity voteEntity) {
        CustomerEntity customerEntity = voteEntity.getCustomer();
        CustomerDTO customer = buildCustomer(customerEntity.getId(), customerEntity.getCpf());

        return VoteDTO.builder()
                .voteChoice(voteEntity.getVoteChoice())
                .customer(customer)
                .build();
    }

    public static VoteDTO build(VoteRequest voteRequest) {
        CustomerRequest customerRequest = voteRequest.getCustomer();
        CustomerDTO customer = buildCustomer(customerRequest.getId(), customerRequest.getCpf());

        return VoteDTO.builder()
                .voteChoice(voteRequest.getChoice())
                .customer(customer)
                .build();
    }

    public static VoteDTO build(VoteEvent voteEvent) {
        CustomerEvent customerEvent = voteEvent.getCustomer();
        CustomerDTO customer = buildCustomer(customerEvent.getId(), customerEvent.getCpf());

        return VoteDTO.builder()
                .voteChoice(voteEvent.getVoteChoice())
                .customer(customer)
                .build();
    }

    private static CustomerDTO buildCustomer(String id, String cpf) {
        return CustomerDTO.builder()
                .id(id)
                .cpf(cpf)
                .build();
    }
}