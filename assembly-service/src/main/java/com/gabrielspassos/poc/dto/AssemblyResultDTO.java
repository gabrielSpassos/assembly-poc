package com.gabrielspassos.poc.dto;

import com.gabrielspassos.poc.enumerator.AssemblyResultEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AssemblyResultDTO {

    private AssemblyResultEnum assemblyResult;
    private Long acceptedVotesCount;
    private Long declinedVotesCount;
    private AssemblyDTO assembly;
    private List<VoteDTO> votes;

}
