package com.gabrielspassos.poc.controller.v1.request;

import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateAssemblyRequest {

    private LocalDateTime expirationDateTime;
    private AssemblyStatusEnum newStatus;

}
