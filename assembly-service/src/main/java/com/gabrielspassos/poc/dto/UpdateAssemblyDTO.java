package com.gabrielspassos.poc.dto;

import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAssemblyDTO {

    private LocalDateTime expirationDateTime;
    private AssemblyStatusEnum newStatus;

}
