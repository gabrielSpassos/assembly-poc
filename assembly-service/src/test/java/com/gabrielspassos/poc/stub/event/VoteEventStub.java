package com.gabrielspassos.poc.stub.event;

import com.gabrielspassos.poc.client.kafka.event.CustomerEvent;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;

public class VoteEventStub {

    public static VoteEvent create() {
        VoteEvent voteEvent = new VoteEvent();
        voteEvent.setAssemblyId("assemblyId");
        voteEvent.setVoteChoice(VoteChoiceEnum.ACCEPTED);
        voteEvent.setCustomer(customerEvent());
        return voteEvent;
    }

    private static CustomerEvent customerEvent() {
        CustomerEvent customerEvent = new CustomerEvent();
        customerEvent.setId("1");
        customerEvent.setCpf("72031483005");
        return customerEvent;
    }
}
