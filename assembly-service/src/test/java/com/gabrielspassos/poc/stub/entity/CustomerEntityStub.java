package com.gabrielspassos.poc.stub.entity;

import com.gabrielspassos.poc.entity.CustomerEntity;

public class CustomerEntityStub {

    public static CustomerEntity create() {
        return create("1", "80050098012");
    }

    public static CustomerEntity create(String id, String cpf) {
        return CustomerEntity.builder()
                .id(id)
                .cpf(cpf)
                .build();
    }
}
