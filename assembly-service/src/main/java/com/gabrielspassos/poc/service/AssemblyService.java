package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.entity.AssemblyEntityBuilder;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.repository.AssemblyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class AssemblyService {

    private final AssemblyRepository assemblyRepository;

    public Mono<AssemblyEntity> createAssembly() {
        AssemblyEntity assemblyEntity = AssemblyEntityBuilder.build();
        return assemblyRepository.save(assemblyEntity);
    }


}
