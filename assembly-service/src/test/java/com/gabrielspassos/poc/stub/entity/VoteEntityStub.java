package com.gabrielspassos.poc.stub.entity;

import com.gabrielspassos.poc.entity.CustomerEntity;
import com.gabrielspassos.poc.entity.VoteEntity;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;

public class VoteEntityStub {

    public static VoteEntity create() {
        return create(VoteChoiceEnum.ACCEPTED, CustomerEntityStub.create());
    }

    public static VoteEntity create(VoteChoiceEnum choice, CustomerEntity customer) {
        return VoteEntity.builder()
                .voteChoice(choice)
                .customer(customer)
                .build();
    }
}
