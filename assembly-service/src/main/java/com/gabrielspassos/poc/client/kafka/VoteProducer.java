package com.gabrielspassos.poc.client.kafka;

import com.gabrielspassos.poc.builder.client.kafka.VoteEventBuilder;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.config.KafkaConfig;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class VoteProducer extends KafkaReactiveProducer {

    private final KafkaConfig kafkaConfig;

    public Mono<VoteDTO> sendVoteToTopic(String assemblyId, VoteDTO voteDTO) {
        VoteEvent voteEvent = VoteEventBuilder.build(assemblyId, voteDTO);
        String message = JsonUtil.getStringJson(voteEvent);
        sendMessages(kafkaConfig.getBootstrapServers(), kafkaConfig.getVotesTopic(), message);
        return Mono.just(voteDTO);
    }

}
