package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.dto.SubmitVoteDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.entity.VoteEntity;

public class VoteDTOBuilder {

    public static VoteDTO build(VoteEntity voteEntity) {
        CustomerDTO customer = CustomerDTOBuilder.build(voteEntity.getCustomer());

        return VoteDTO.builder()
                .id(voteEntity.getId())
                .assemblyId(voteEntity.getAssemblyId())
                .voteChoice(voteEntity.getVoteChoice())
                .customer(customer)
                .build();
    }

    public static VoteDTO build(String assemblyId, SubmitVoteDTO submitVoteDTO) {
        CustomerDTO customer = submitVoteDTO.getCustomer();

        return VoteDTO.builder()
                .id(null)
                .assemblyId(assemblyId)
                .voteChoice(submitVoteDTO.getChoice())
                .customer(customer)
                .build();
    }
}
