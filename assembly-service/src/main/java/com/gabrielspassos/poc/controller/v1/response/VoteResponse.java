package com.gabrielspassos.poc.controller.v1.response;

import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import lombok.Data;

@Data
public class VoteResponse {

    private VoteChoiceEnum voteChoice;
    private CustomerResponse customer;

}
