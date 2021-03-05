package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.entity.VoteEntity;

public class VoteEntityBuilder {

    public static VoteEntity build(VoteDTO voteDTO) {
        CustomerDTO customerDTO = voteDTO.getCustomer();
        CustomerEntity customer = buildCustomer(customerDTO.getId(), customerDTO.getCpf());

        return VoteEntity.builder()
                .voteChoice(voteDTO.getVoteChoice())
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
