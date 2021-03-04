package com.gabrielspassos.poc.controller.v1.request;

import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import lombok.Data;

@Data
public class VoteRequest {

    private VoteChoiceEnum choice;
    private CustomerRequest customer;

}
