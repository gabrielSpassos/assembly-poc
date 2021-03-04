package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.entity.AssemblyEntityBuilder;
import com.gabrielspassos.poc.config.AssemblyConfig;
import com.gabrielspassos.poc.controller.v1.request.UpdateAssemblyRequest;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.exception.NotFoundAssemblyException;
import com.gabrielspassos.poc.mapper.AssemblyEntityMapper;
import com.gabrielspassos.poc.repository.AssemblyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class AssemblyService {

    private final AssemblyConfig assemblyConfig;
    private final AssemblyRepository assemblyRepository;

    public Mono<AssemblyEntity> createAssembly() {
        AssemblyEntity assemblyEntity = AssemblyEntityBuilder.build();
        return assemblyRepository.save(assemblyEntity);
    }

    public Mono<AssemblyEntity> updateAssembly(String assemblyId, UpdateAssemblyRequest updateAssemblyRequest) {
        return getAssemblyById(assemblyId)
                .map(assemblyEntity -> AssemblyEntityMapper.map(
                        assemblyEntity, updateAssemblyRequest, assemblyConfig.getAssemblyDefaultExpirationMinutes()))
                .flatMap(assemblyRepository::save);
    }

    public Mono<AssemblyEntity> getAssemblyById(String assemblyId) {
        return assemblyRepository.findById(assemblyId)
                .switchIfEmpty(Mono.error(new NotFoundAssemblyException()));
    }

    public Flux<AssemblyEntity> getAssemblies(Pageable page) {
        return assemblyRepository.findAllBy(page);
    }

}
