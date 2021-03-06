package com.gabrielspassos.poc.stub.dto;

import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;

public class VoteDTOStub {

    public static VoteDTO create() {
        return create(null, "id", VoteChoiceEnum.ACCEPTED, CustomerDTOStub.create());
    }

    public static VoteDTO create(String voteId, String assemblyId, VoteChoiceEnum choice, CustomerDTO customer) {
        return VoteDTO.builder()
                .id(voteId)
                .assemblyId(assemblyId)
                .voteChoice(choice)
                .customer(customer)
                .build();
    }

}
