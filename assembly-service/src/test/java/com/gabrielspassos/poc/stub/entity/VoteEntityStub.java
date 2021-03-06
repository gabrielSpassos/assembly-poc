package com.gabrielspassos.poc.stub.entity;

import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.entity.VoteEntity;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;

public class VoteEntityStub {

    public static VoteEntity create() {
        return create("voteId", "assemblyId", VoteChoiceEnum.ACCEPTED, CustomerEntityStub.create());
    }

    public static VoteEntity create(String id, String assemblyId, VoteChoiceEnum choice, CustomerEntity customer) {
        return VoteEntity.builder()
                .id(id)
                .assemblyId(assemblyId)
                .voteChoice(choice)
                .customer(customer)
                .build();
    }
}
