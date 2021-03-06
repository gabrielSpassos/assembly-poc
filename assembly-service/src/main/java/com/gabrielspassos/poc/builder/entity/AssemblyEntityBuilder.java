package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.CreateAssemblyDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import com.gabrielspassos.poc.util.DateTimeUtil;

public class AssemblyEntityBuilder {

    public static AssemblyEntity build(CreateAssemblyDTO createAssemblyDTO) {
        return AssemblyEntity.builder()
                .id(null)
                .name(createAssemblyDTO.getName())
                .description(createAssemblyDTO.getDescription())
                .status(AssemblyStatusEnum.CLOSED)
                .registerDateTime(DateTimeUtil.now())
                .expirationDateTime(null)
                .updateDateTime(null)
                .build();
    }

    public static AssemblyEntity build(AssemblyDTO assemblyDTO) {
        return AssemblyEntity.builder()
                .id(assemblyDTO.getId())
                .name(assemblyDTO.getName())
                .description(assemblyDTO.getDescription())
                .status(assemblyDTO.getStatus())
                .expirationDateTime(assemblyDTO.getExpirationDateTime())
                .registerDateTime(assemblyDTO.getRegisterDateTime())
                .updateDateTime(assemblyDTO.getUpdateDateTime())
                .build();
    }
}
