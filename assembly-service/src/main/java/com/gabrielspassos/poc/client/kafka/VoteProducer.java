package com.gabrielspassos.poc.client.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielspassos.poc.builder.client.kafka.VoteRequestBuilder;
import com.gabrielspassos.poc.client.kafka.request.VoteRequest;
import com.gabrielspassos.poc.config.KafkaConfig;
import com.gabrielspassos.poc.entity.VoteEntity;
import com.gabrielspassos.poc.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class VoteProducer extends KafkaReactiveProducer {

    private final KafkaConfig kafkaConfig;

    public Mono<VoteEntity> sendVoteToTopic(String assemblyId, VoteEntity voteEntity) {
        VoteRequest voteRequest = VoteRequestBuilder.build(assemblyId, voteEntity);
        String message = JsonUtil.getStringJson(voteRequest);
        return sendMessages(kafkaConfig.getBootstrapServers(), kafkaConfig.getVotesTopic(), message)
                .thenReturn(voteEntity);
    }

}
