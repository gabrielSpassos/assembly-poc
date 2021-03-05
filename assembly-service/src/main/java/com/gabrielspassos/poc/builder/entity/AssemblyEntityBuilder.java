package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.CreateAssemblyDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.entity.VoteEntity;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import com.gabrielspassos.poc.util.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssemblyEntityBuilder {

    public static AssemblyEntity build(CreateAssemblyDTO createAssemblyDTO) {
        return AssemblyEntity.builder()
                .id(null)
                .name(createAssemblyDTO.getName())
                .description(createAssemblyDTO.getDescription())
                .status(AssemblyStatusEnum.CLOSED)
                .expirationDateTime(null)
                .registerDateTime(DateTimeUtil.now())
                .votes(new ArrayList<>())
                .build();
    }

    public static AssemblyEntity build(AssemblyDTO assemblyDTO) {
        List<VoteEntity> votes = assemblyDTO.getVotes().stream()
                .map(VoteEntityBuilder::build)
                .collect(Collectors.toList());

        return AssemblyEntity.builder()
                .id(assemblyDTO.getId())
                .name(assemblyDTO.getName())
                .description(assemblyDTO.getDescription())
                .status(assemblyDTO.getStatus())
                .expirationDateTime(assemblyDTO.getExpirationDateTime())
                .registerDateTime(assemblyDTO.getRegisterDateTime())
                .votes(votes)
                .build();
    }
}
