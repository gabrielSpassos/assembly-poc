package com.gabrielspassos.poc.controller.v1.response;

import com.gabrielspassos.poc.enumerator.AssemblyResultEnum;
import lombok.Data;

import java.util.List;

@Data
public class AssemblyResultResponse {

    private AssemblyResultEnum assemblyResult;
    private Long acceptedVotesCount;
    private Long declinedVotesCount;
    private AssemblyResponse assembly;
    private List<VoteResponse> votes;

}
