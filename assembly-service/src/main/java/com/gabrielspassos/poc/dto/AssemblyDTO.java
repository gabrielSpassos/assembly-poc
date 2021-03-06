package com.gabrielspassos.poc.dto;

import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AssemblyDTO {

    private String id;
    private String name;
    private String description;
    private AssemblyStatusEnum status;
    private LocalDateTime registerDateTime;
    private LocalDateTime updateDateTime;
    private LocalDateTime expirationDateTime;

}
