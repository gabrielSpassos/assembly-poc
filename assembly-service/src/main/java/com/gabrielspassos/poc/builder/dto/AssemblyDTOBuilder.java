package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;

import java.util.List;
import java.util.stream.Collectors;

public class AssemblyDTOBuilder {

    public static AssemblyDTO build(AssemblyEntity assemblyEntity) {
        List<VoteDTO> votes = assemblyEntity.getVotes().stream()
                .map(VoteDTOBuilder::build)
                .collect(Collectors.toList());

        return AssemblyDTO.builder()
                .id(assemblyEntity.getId())
                .name(assemblyEntity.getName())
                .description(assemblyEntity.getDescription())
                .status(assemblyEntity.getStatus())
                .registerDateTime(assemblyEntity.getRegisterDateTime())
                .updateDateTime(assemblyEntity.getUpdateDateTime())
                .expirationDateTime(assemblyEntity.getExpirationDateTime())
                .votes(votes)
                .build();
    }
}
