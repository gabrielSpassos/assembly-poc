package com.gabrielspassos.poc.stub.dto;

import com.gabrielspassos.poc.dto.UpdateAssemblyDTO;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;

import java.time.LocalDateTime;

public class UpdateAssemblyDTOStub {

    public static UpdateAssemblyDTO create(LocalDateTime expiration) {
        return new UpdateAssemblyDTO(expiration, AssemblyStatusEnum.OPEN);
    }
}
