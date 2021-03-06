package com.gabrielspassos.poc.stub.dto;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.AssemblyResultDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.enumerator.AssemblyResultEnum;
import org.assertj.core.util.Lists;

public class AssemblyResultDTOStub {

    public static AssemblyResultDTO create(AssemblyResultEnum result, Long acceptedVotesCount, Long declinedVotesCount,
                                           AssemblyDTO assemblyDTO, VoteDTO... votes) {
        return AssemblyResultDTO.builder()
                .assemblyResult(result)
                .acceptedVotesCount(acceptedVotesCount)
                .declinedVotesCount(declinedVotesCount)
                .assembly(assemblyDTO)
                .votes(Lists.newArrayList(votes))
                .build();
    }
}
