package com.gabrielspassos.poc.stub.dto;

import com.gabrielspassos.poc.dto.CustomerDTO;

public class CustomerDTOStub {

    public static CustomerDTO create() {
        return create("1", "80050098012");
    }

    public static CustomerDTO create(String id, String cpf) {
        return CustomerDTO.builder()
                .id(id)
                .cpf(cpf)
                .build();
    }

}
