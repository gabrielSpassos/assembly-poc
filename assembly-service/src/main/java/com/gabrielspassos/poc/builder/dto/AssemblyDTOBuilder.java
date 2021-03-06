package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;

public class AssemblyDTOBuilder {

    public static AssemblyDTO build(AssemblyEntity assemblyEntity) {
        return AssemblyDTO.builder()
                .id(assemblyEntity.getId())
                .name(assemblyEntity.getName())
                .description(assemblyEntity.getDescription())
                .status(assemblyEntity.getStatus())
                .registerDateTime(assemblyEntity.getRegisterDateTime())
                .updateDateTime(assemblyEntity.getUpdateDateTime())
                .expirationDateTime(assemblyEntity.getExpirationDateTime())
                .build();
    }
}
