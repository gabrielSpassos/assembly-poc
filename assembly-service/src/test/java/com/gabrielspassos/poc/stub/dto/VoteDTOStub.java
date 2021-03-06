package com.gabrielspassos.poc.stub.dto;

import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;

public class VoteDTOStub {

    public static VoteDTO create() {
        return create("id", VoteChoiceEnum.ACCEPTED, CustomerDTOStub.create());
    }

    public static VoteDTO create(String assemblyId, VoteChoiceEnum choice, CustomerDTO customer) {
        return VoteDTO.builder()
                .id(null)
                .assemblyId(assemblyId)
                .voteChoice(choice)
                .customer(customer)
                .build();
    }

}
