package com.gabrielspassos.poc.stub.dto;

import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;

public class VoteDTOStub {

    public static VoteDTO create() {
        return create(VoteChoiceEnum.ACCEPTED, CustomerDTOStub.create());
    }

    public static VoteDTO create(VoteChoiceEnum choice, CustomerDTO customer) {
        return VoteDTO.builder()
                .voteChoice(choice)
                .customer(customer)
                .build();
    }

}
