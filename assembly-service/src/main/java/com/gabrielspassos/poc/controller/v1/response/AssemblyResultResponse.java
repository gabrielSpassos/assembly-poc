package com.gabrielspassos.poc.controller.v1.response;

import com.gabrielspassos.poc.enumerator.AssemblyResultEnum;
import lombok.Data;

@Data
public class AssemblyResultResponse {

    private AssemblyResultEnum assemblyResult;
    private Long acceptedVotesCount;
    private Long declinedVotesCount;
    private AssemblyResponse assembly;

}
