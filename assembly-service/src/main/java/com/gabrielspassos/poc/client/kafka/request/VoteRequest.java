package com.gabrielspassos.poc.client.kafka.request;

import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {

    private String assemblyId;
    private VoteChoiceEnum voteChoice;
    private CustomerRequest customer;

}
