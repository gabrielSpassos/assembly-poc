package com.gabrielspassos.poc.client.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.service.VoteService;
import com.gabrielspassos.poc.stub.dto.VoteDTOStub;
import com.gabrielspassos.poc.stub.event.VoteEventStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VoteConsumerTest {

    @InjectMocks
    private VoteConsumer voteConsumer;
    @Mock
    private VoteService voteService;
    @Spy
    private ObjectMapper objectMapper;
    @Mock
    private Acknowledgment ack;

    @Test
    public void shouldCreateVote() {
        String event = "{\"assemblyId\":\"assemblyId\",\"voteChoice\":\"ACCEPTED\",\"customer\":{\"id\":\"1\",\"cpf\":\"80050098012\"}}";
        VoteEvent voteEvent = VoteEventStub.create();
        VoteDTO voteDTO = VoteDTOStub.create();

        given(voteService.createVote(voteEvent))
                .willReturn(Mono.just(voteDTO));

        voteConsumer.listen(event, ack, "0", "0");

        verify(voteService).createVote(voteEvent);
        verify(ack).acknowledge();
    }

    @Test
    public void shouldThrowErrorToParseEvent() {
        assertThrows(IllegalStateException.class, () -> voteConsumer.listen("", ack, "0", "0"));

        verify(voteService,never()).createVote(any());
        verify(ack, never()).acknowledge();
    }
}