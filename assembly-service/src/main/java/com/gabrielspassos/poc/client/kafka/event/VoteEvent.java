package com.gabrielspassos.poc.client.kafka.event;

import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteEvent {

    private String assemblyId;
    private VoteChoiceEnum voteChoice;
    private CustomerEvent customer;

}
