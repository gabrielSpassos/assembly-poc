package com.gabrielspassos.poc.stub.event;

import com.gabrielspassos.poc.client.kafka.event.CustomerEvent;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;

public class VoteEventStub {

    public static VoteEvent create() {
        return create("assemblyId", VoteChoiceEnum.ACCEPTED, customerEvent());
    }

    public static VoteEvent create(String assemblyId, VoteChoiceEnum voteChoice, CustomerEvent customerEvent) {
        VoteEvent voteEvent = new VoteEvent();
        voteEvent.setAssemblyId(assemblyId);
        voteEvent.setVoteChoice(voteChoice);
        voteEvent.setCustomer(customerEvent);
        return voteEvent;
    }

    public static CustomerEvent customerEvent(String id, String cpf) {
        CustomerEvent customerEvent = new CustomerEvent();
        customerEvent.setId(id);
        customerEvent.setCpf(cpf);
        return customerEvent;
    }

    private static CustomerEvent customerEvent() {
        return customerEvent("1", "80050098012");
    }
}
