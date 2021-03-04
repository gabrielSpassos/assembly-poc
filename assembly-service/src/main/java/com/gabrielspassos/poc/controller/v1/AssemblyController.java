package com.gabrielspassos.poc.controller.v1;

import com.gabrielspassos.poc.controller.v1.request.VoteRequest;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.entity.VoteEntity;
import com.gabrielspassos.poc.service.AssemblyService;
import com.gabrielspassos.poc.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class AssemblyController implements BaseVersion {

    private final AssemblyService assemblyService;
    private final VoteService voteService;

    @PostMapping("/assemblies")
    public Mono<AssemblyEntity> createAssembly() {
        log.info("Iniciado a criação de assembleia");

        return assemblyService.createAssembly();
    }

    @GetMapping("/assemblies/{assemblyId}")
    public Mono<AssemblyEntity> getAssemblyById(@PathVariable("assemblyId") String assemblyId) {
        return assemblyService.getAssemblyById(assemblyId)
                .doOnSuccess(response -> log.info("Encontrado assembleia {}", response));
    }

    @PostMapping("/assemblies/{assemblyId}/votes")
    public Mono<VoteEntity> submitVoteAtAssembly(@PathVariable("assemblyId") String assemblyId,
                                                 @RequestBody VoteRequest voteRequest) {
        log.info("Criando voto em assembleia");
        return voteService.submitVote(assemblyId, voteRequest);
    }
}
