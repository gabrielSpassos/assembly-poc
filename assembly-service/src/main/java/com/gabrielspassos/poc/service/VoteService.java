package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.entity.VoteEntityBuilder;
import com.gabrielspassos.poc.client.kafka.VoteProducer;
import com.gabrielspassos.poc.controller.v1.request.VoteRequest;
import com.gabrielspassos.poc.entity.VoteEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class VoteService {

    private final AssemblyService assemblyService;
    private final VoteProducer voteProducer;

    public Mono<VoteEntity> submitVote(String assemblyId, VoteRequest voteRequest) {
        VoteEntity voteEntity = VoteEntityBuilder.build(voteRequest);

        return assemblyService.getAssemblyById(assemblyId)
                .flatMap(assemblyEntity -> voteProducer.sendVoteToTopic(assemblyEntity.getId(), voteEntity));
    }

}
