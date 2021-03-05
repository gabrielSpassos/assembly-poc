package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.dto.AssemblyResultDTOBuilder;
import com.gabrielspassos.poc.builder.entity.AssemblyEntityBuilder;
import com.gabrielspassos.poc.config.AssemblyConfig;
import com.gabrielspassos.poc.controller.v1.request.UpdateAssemblyRequest;
import com.gabrielspassos.poc.dto.AssemblyResultDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.entity.VoteEntity;
import com.gabrielspassos.poc.enumerator.AssemblyResultEnum;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import com.gabrielspassos.poc.exception.NotFoundAssemblyException;
import com.gabrielspassos.poc.mapper.AssemblyEntityMapper;
import com.gabrielspassos.poc.repository.AssemblyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Service
@AllArgsConstructor
public class AssemblyService {

    private final AssemblyConfig assemblyConfig;
    private final AssemblyRepository assemblyRepository;

    public Mono<AssemblyEntity> createAssembly() {
        AssemblyEntity assemblyEntity = AssemblyEntityBuilder.build();
        return saveAssembly(assemblyEntity);
    }

    public Mono<AssemblyEntity> updateAssembly(String assemblyId, UpdateAssemblyRequest updateAssemblyRequest) {
        return getAssemblyById(assemblyId)
                .map(assemblyEntity -> AssemblyEntityMapper.map(
                        assemblyEntity, updateAssemblyRequest, assemblyConfig.getAssemblyDefaultExpirationMinutes()))
                .flatMap(this::saveAssembly);
    }

    public Mono<AssemblyEntity> getAssemblyById(String assemblyId) {
        return assemblyRepository.findById(assemblyId)
                .switchIfEmpty(Mono.error(new NotFoundAssemblyException()));
    }

    public Mono<AssemblyResultDTO> getAssemblyResult(String assemblyId) {
        return getAssemblyById(assemblyId)
                .map(this::buildAssemblyResult);
    }

    public Flux<AssemblyEntity> getAssemblies(Pageable page) {
        return assemblyRepository.findAllBy(page);
    }

    Mono<AssemblyEntity> saveAssembly(AssemblyEntity assemblyEntity) {
        return assemblyRepository.save(assemblyEntity)
                .doOnSuccess(entity -> log.info("Salvo assembleia {}", entity));
    }

    private AssemblyResultDTO buildAssemblyResult(AssemblyEntity assemblyEntity) {
        List<VoteEntity> votes = assemblyEntity.getVotes();
        Long acceptedVotesCount = votes.stream().filter(getAcceptedVotes()).count();
        Long declinedVotesCount = votes.stream().filter(getDeclinedVotes()).count();

        AssemblyResultEnum assemblyResult = AssemblyResultEnum.getResult(acceptedVotesCount, declinedVotesCount);

        return AssemblyResultDTOBuilder.build(assemblyEntity, assemblyResult, acceptedVotesCount, declinedVotesCount);
    }

    private Predicate<VoteEntity> getAcceptedVotes() {
        return voteEntity -> VoteChoiceEnum.ACCEPTED.equals(voteEntity.getVoteChoice());
    }

    private Predicate<VoteEntity> getDeclinedVotes() {
        return voteEntity -> VoteChoiceEnum.DECLINED.equals(voteEntity.getVoteChoice());
    }

}
