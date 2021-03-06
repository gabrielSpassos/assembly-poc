package com.gabrielspassos.poc.mapper;

import com.gabrielspassos.poc.controller.v1.request.UpdateAssemblyRequest;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import com.gabrielspassos.poc.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.Objects;

public class AssemblyEntityMapper {

    public static AssemblyEntity map(AssemblyEntity assemblyEntity, UpdateAssemblyRequest updateAssemblyRequest,
                                     Long defaultExpirationMinutes) {
        assemblyEntity.setStatus(updateAssemblyRequest.getNewStatus());
        assemblyEntity.setExpirationDateTime(getExpirationDateTime(updateAssemblyRequest, defaultExpirationMinutes));
        assemblyEntity.setUpdateDateTime(DateTimeUtil.now());
        return assemblyEntity;
    }

    public static AssemblyEntity map(AssemblyEntity assemblyEntity, AssemblyStatusEnum status) {
        assemblyEntity.setStatus(status);
        assemblyEntity.setUpdateDateTime(DateTimeUtil.now());
        return assemblyEntity;
    }

    private static LocalDateTime getExpirationDateTime(UpdateAssemblyRequest updateAssemblyRequest, Long defaultExpirationMinutes) {
        return Objects.nonNull(updateAssemblyRequest.getExpirationDateTime())
                ? updateAssemblyRequest.getExpirationDateTime()
                : DateTimeUtil.now().plusMinutes(defaultExpirationMinutes);
    }
}
