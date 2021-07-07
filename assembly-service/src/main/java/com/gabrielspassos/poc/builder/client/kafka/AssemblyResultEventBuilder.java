package com.gabrielspassos.poc.builder.client.kafka;

import com.gabrielspassos.poc.client.kafka.event.AssemblyResultEvent;
import com.gabrielspassos.poc.dto.AssemblyResultDTO;

public class AssemblyResultEventBuilder {

    public static AssemblyResultEvent build(AssemblyResultDTO assemblyResultDTO) {
        return AssemblyResultEvent.builder()
                .assemblyId(assemblyResultDTO.getAssembly().getId())
                .assemblyName(assemblyResultDTO.getAssembly().getName())
                .assemblyResult(assemblyResultDTO.getAssemblyResult())
                .acceptedVotesCount(assemblyResultDTO.getAcceptedVotesCount())
                .declinedVotesCount(assemblyResultDTO.getDeclinedVotesCount())
                .build();
    }
}
