package com.gabrielspassos.poc.client.kafka.event;

import com.gabrielspassos.poc.enumerator.AssemblyResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssemblyResultEvent {

    private String assemblyId;
    private String assemblyName;
    private AssemblyResultEnum assemblyResult;
    private Long acceptedVotesCount;
    private Long declinedVotesCount;

}
