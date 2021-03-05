package com.gabrielspassos.poc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {

    private String id;
    private String cpf;

}
