package com.gabrielspassos.poc.entity;

import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteEntity {

    private VoteChoiceEnum voteChoice;
    private CustomerEntity customer;

}
