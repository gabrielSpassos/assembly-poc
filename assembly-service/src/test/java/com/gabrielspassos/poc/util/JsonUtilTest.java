package com.gabrielspassos.poc.util;

import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.stub.event.VoteEventStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    public void shouldReturnJsonAsString() {
        VoteEvent voteEvent = VoteEventStub.create();

        String stringJson = JsonUtil.getStringJson(voteEvent);

        assertEquals("{\"assemblyId\":\"assemblyId\",\"voteChoice\":\"ACCEPTED\",\"customer\":{\"id\":\"1\",\"cpf\":\"72031483005\"}}", stringJson);
    }

}