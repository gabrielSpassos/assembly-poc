package com.gabrielspassos.poc.client.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class KafkaReactiveProducer {

    private static final String CLIENT_ID = "assembly-service";
    private static final String ACKS_CONFIG = "all";
    private static final String CORRELATION_RESPONSE = "notUsed";

    Mono<SenderResult<Object>> sendMessages(String server, String topic, String message) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID + UUID.randomUUID());
        props.put(ProducerConfig.ACKS_CONFIG, ACKS_CONFIG);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        SenderOptions<Integer, String> senderOptions = SenderOptions.create(props);

        KafkaSender<Integer, String> kafkaProducer = KafkaSender.create(senderOptions);

        return Mono.just(buildRecord(topic, message))
                .flatMapMany(senderRecord -> kafkaProducer.send(Mono.just(senderRecord)))
                .next()
                .doOnSuccess(result -> log.info("Mensagem enviada ao kafka {}", message))
                .doOnError(throwable -> log.error("Erro ao enviar mensagem ao kafka", throwable));
    }

    private SenderRecord<Integer, String, Object> buildRecord(String topic, String message) {
        ProducerRecord<Integer, String> producerRecord = new ProducerRecord<>(topic, message);
        return SenderRecord.create(producerRecord, CORRELATION_RESPONSE);
    }
}
