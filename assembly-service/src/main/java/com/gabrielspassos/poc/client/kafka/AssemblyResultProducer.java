package com.gabrielspassos.poc.client.kafka;

import com.gabrielspassos.poc.builder.client.kafka.AssemblyResultEventBuilder;
import com.gabrielspassos.poc.client.kafka.event.AssemblyResultEvent;
import com.gabrielspassos.poc.config.KafkaConfig;
import com.gabrielspassos.poc.dto.AssemblyResultDTO;
import com.gabrielspassos.poc.util.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AssemblyResultProducer extends KafkaReactiveProducer {

    private final KafkaConfig kafkaConfig;

    public Mono<AssemblyResultDTO> sendAssemblyResultToTopic(AssemblyResultDTO assemblyResultDTO) {
        AssemblyResultEvent assemblyResultEvent = AssemblyResultEventBuilder.build(assemblyResultDTO);
        String message = JsonUtil.getStringJson(assemblyResultEvent);
        sendMessages(kafkaConfig.getBootstrapServers(), kafkaConfig.getAssemblyResultTopic(), message);
        return Mono.just(assemblyResultDTO);
    }

}
