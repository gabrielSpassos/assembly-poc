package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.AssemblyResultDTO;
import com.gabrielspassos.poc.enumerator.AssemblyResultEnum;

public class AssemblyResultDTOBuilder {

    public static AssemblyResultDTO build(AssemblyDTO assemblyDTO, AssemblyResultEnum assemblyResult,
                                          Long acceptedVotesCount, Long declinedVotesCount) {
        return AssemblyResultDTO.builder()
                .assemblyResult(assemblyResult)
                .acceptedVotesCount(acceptedVotesCount)
                .declinedVotesCount(declinedVotesCount)
                .assembly(assemblyDTO)
                .build();
    }

}
