package com.gabrielspassos.poc.builder.client.kafka;

import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import com.gabrielspassos.poc.stub.dto.VoteDTOStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VoteEventBuilderTest {

    @Test
    public void shouldCreateVoteEvent() {
        VoteEvent voteEvent = VoteEventBuilder.build("assemblyId", VoteDTOStub.create());

        assertEquals("assemblyId", voteEvent.getAssemblyId());
        assertEquals(VoteChoiceEnum.ACCEPTED, voteEvent.getVoteChoice());
        assertEquals("1", voteEvent.getCustomer().getId());
        assertEquals("80050098012", voteEvent.getCustomer().getCpf());
    }

}