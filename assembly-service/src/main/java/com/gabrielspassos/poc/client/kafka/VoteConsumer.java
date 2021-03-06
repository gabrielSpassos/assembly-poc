package com.gabrielspassos.poc.client.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteConsumer {

    private final VoteService voteService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.votes-topic}", groupId = "${kafka.consumer-group}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String event, Acknowledgment ack,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String partition, @Header(KafkaHeaders.OFFSET) String offset) {
        try {
            log.info("Partição {}, Offset {}, Mensagem: {}", partition, offset, event);
            VoteEvent voteEvent = convertValue(event);
            voteService.addVoteToAssembly(voteEvent)
                    .doOnSuccess(voteDTO -> log.info("Voto salvo {}", voteDTO))
                    .doFinally(signalType -> ack.acknowledge())
                    .subscribe();
        } catch (Exception e) {
            log.error(String.format("Erro ao processar a mensagem de resposta de pagamento %s", event), e);
        }
    }

    private VoteEvent convertValue(String event) {
        try {
            return objectMapper.readValue(event, VoteEvent.class);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

}

