package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.controller.v1.request.VoteRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.SubmitVoteDTO;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;

public class SubmitVoteDTOBuilder {

    public static SubmitVoteDTO build(VoteRequest voteRequest) {
        VoteChoiceEnum choice = VoteChoiceEnum.getVoteByCustomerChoice(voteRequest.getChoice());
        CustomerDTO customer = CustomerDTOBuilder.build(voteRequest.getCustomer());

        return new SubmitVoteDTO(choice, customer);
    }
}
