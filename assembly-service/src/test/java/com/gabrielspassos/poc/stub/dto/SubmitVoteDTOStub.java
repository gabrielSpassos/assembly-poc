package com.gabrielspassos.poc.stub.dto;

import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.SubmitVoteDTO;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;

public class SubmitVoteDTOStub {

    public static SubmitVoteDTO create() {
        return create(VoteChoiceEnum.ACCEPTED, CustomerDTOStub.create());
    }

    public static SubmitVoteDTO create(VoteChoiceEnum voteChoice, CustomerDTO customer) {
        SubmitVoteDTO submitVoteDTO = new SubmitVoteDTO();
        submitVoteDTO.setChoice(voteChoice);
        submitVoteDTO.setCustomer(customer);
        return submitVoteDTO;
    }
}
