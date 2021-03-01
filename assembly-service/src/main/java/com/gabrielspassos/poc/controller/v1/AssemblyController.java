package com.gabrielspassos.poc.controller.v1;

import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.service.AssemblyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class AssemblyController implements BaseVersion {

    private final AssemblyService assemblyService;

    @PostMapping("/assemblies")
    public Mono<AssemblyEntity> createAssembly() {
        log.info("Iniciado a criação de assembleia");

        return assemblyService.createAssembly();
    }
}
