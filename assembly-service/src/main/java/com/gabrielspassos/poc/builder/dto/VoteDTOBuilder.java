package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.SubmitVoteDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.entity.VoteEntity;

public class VoteDTOBuilder {

    public static VoteDTO build(VoteEntity voteEntity) {
        CustomerDTO customer = CustomerDTOBuilder.build(voteEntity.getCustomer());

        return VoteDTO.builder()
                .voteChoice(voteEntity.getVoteChoice())
                .customer(customer)
                .build();
    }

    public static VoteDTO build(SubmitVoteDTO submitVoteDTO) {
        CustomerDTO customer = submitVoteDTO.getCustomer();

        return VoteDTO.builder()
                .voteChoice(submitVoteDTO.getChoice())
                .customer(customer)
                .build();
    }

    public static VoteDTO build(VoteEvent voteEvent) {
        CustomerDTO customer = CustomerDTOBuilder.build(voteEvent.getCustomer());

        return VoteDTO.builder()
                .voteChoice(voteEvent.getVoteChoice())
                .customer(customer)
                .build();
    }
}
