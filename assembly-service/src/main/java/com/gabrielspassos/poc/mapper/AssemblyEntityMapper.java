package com.gabrielspassos.poc.mapper;

import com.gabrielspassos.poc.dto.UpdateAssemblyDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import com.gabrielspassos.poc.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.Objects;

public class AssemblyEntityMapper {

    public static AssemblyEntity map(AssemblyEntity assemblyEntity, UpdateAssemblyDTO updateAssemblyDTO,
                                     Long defaultExpirationMinutes) {
        assemblyEntity.setStatus(updateAssemblyDTO.getNewStatus());
        assemblyEntity.setExpirationDateTime(getExpirationDateTime(updateAssemblyDTO, defaultExpirationMinutes));
        assemblyEntity.setUpdateDateTime(DateTimeUtil.now());
        return assemblyEntity;
    }

    public static AssemblyEntity map(AssemblyEntity assemblyEntity, AssemblyStatusEnum status) {
        assemblyEntity.setStatus(status);
        assemblyEntity.setUpdateDateTime(DateTimeUtil.now());
        return assemblyEntity;
    }

    private static LocalDateTime getExpirationDateTime(UpdateAssemblyDTO updateAssemblyDTO, Long defaultExpirationMinutes) {
        return Objects.nonNull(updateAssemblyDTO.getExpirationDateTime())
                ? updateAssemblyDTO.getExpirationDateTime()
                : DateTimeUtil.now().plusMinutes(defaultExpirationMinutes);
    }
}
