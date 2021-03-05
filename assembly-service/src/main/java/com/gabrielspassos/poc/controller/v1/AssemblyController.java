package com.gabrielspassos.poc.controller.v1;

import com.gabrielspassos.poc.controller.v1.request.UpdateAssemblyRequest;
import com.gabrielspassos.poc.controller.v1.request.VoteRequest;
import com.gabrielspassos.poc.dto.AssemblyResultDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.entity.VoteEntity;
import com.gabrielspassos.poc.service.AssemblyService;
import com.gabrielspassos.poc.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.gabrielspassos.poc.config.PageConfiguration.DEFAULT_PAGE;
import static com.gabrielspassos.poc.config.PageConfiguration.DEFAULT_SIZE;

@Slf4j
@RestController
@AllArgsConstructor
public class AssemblyController implements BaseVersion {

    private final AssemblyService assemblyService;
    private final VoteService voteService;

    @GetMapping("/assemblies")
    public Flux<AssemblyEntity> getAssemblies(@RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page,
                                              @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size) {
        log.info("Iniciado busca de assembleias na página {} e tamanho {}", page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        return assemblyService.getAssemblies(pageRequest);
    }

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

    @PatchMapping("/assemblies/{assemblyId}")
    public Mono<AssemblyEntity> updateAssemblyById(@PathVariable("assemblyId") String assemblyId,
                                                   @RequestBody UpdateAssemblyRequest updateAssemblyRequest) {
        return assemblyService.updateAssembly(assemblyId, updateAssemblyRequest)
                .doOnSuccess(response -> log.info("Atualizado assembleia {}", response));
    }

    @PostMapping("/assemblies/{assemblyId}/votes")
    public Mono<VoteEntity> submitVoteAtAssembly(@PathVariable("assemblyId") String assemblyId,
                                                 @RequestBody VoteRequest voteRequest) {
        log.info("Criando voto em assembleia");
        return voteService.submitVote(assemblyId, voteRequest)
                .doOnSuccess(response -> log.info("Criado voto {}", response));
    }

    @GetMapping("/assemblies/{assemblyId}/results")
    public Mono<AssemblyResultDTO> getAssemblyResult(@PathVariable("assemblyId") String assemblyId) {
        return assemblyService.getAssemblyResult(assemblyId)
                .doOnSuccess(response -> log.info("Resultado da assembleia {}", response));
    }
}
