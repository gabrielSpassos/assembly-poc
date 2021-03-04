package com.gabrielspassos.poc.builder.entity;

import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import com.gabrielspassos.poc.util.DateTimeUtil;

public class AssemblyEntityBuilder {

    public static AssemblyEntity build() {
        return AssemblyEntity.builder()
                .id(null)
                .status(AssemblyStatusEnum.CLOSED)
                .expirationDateTime(null)
                .registerDateTime(DateTimeUtil.now())
                .votes(null)
                .build();
    }
}
