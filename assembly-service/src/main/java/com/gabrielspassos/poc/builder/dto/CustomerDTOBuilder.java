package com.gabrielspassos.poc.builder.dto;

import com.gabrielspassos.poc.controller.v1.request.CustomerRequest;
import com.gabrielspassos.poc.dto.CustomerDTO;
import com.gabrielspassos.poc.entity.CustomerEntity;

public class CustomerDTOBuilder {

    public static CustomerDTO build(CustomerRequest customerRequest) {
        return buildCustomer(customerRequest.getId(), customerRequest.getCpf());
    }

    public static CustomerDTO build(CustomerEntity customerEntity) {
        return buildCustomer(customerEntity.getId(), customerEntity.getCpf());
    }

    private static CustomerDTO buildCustomer(String id, String cpf) {
        return CustomerDTO.builder()
                .id(id)
                .cpf(cpf)
                .build();
    }

}
