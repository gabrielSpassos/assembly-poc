package com.gabrielspassos.poc.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielspassos.poc.controller.v1.request.CreateAssemblyRequest;
import com.gabrielspassos.poc.controller.v1.request.UpdateAssemblyRequest;
import com.gabrielspassos.poc.controller.v1.request.VoteRequest;
import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.AssemblyResultDTO;
import com.gabrielspassos.poc.dto.CreateAssemblyDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
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
    private final ObjectMapper objectMapper;

    @GetMapping("/assemblies")
    public Flux<AssemblyDTO> getAssemblies(@RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page,
                                           @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size) {
        log.info("Iniciado busca de assembleias na página {} e tamanho {}", page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        return assemblyService.getAssemblies(pageRequest);
    }

    @PostMapping("/assemblies")
    public Mono<AssemblyDTO> createAssembly(@RequestBody CreateAssemblyRequest createAssemblyRequest) {
        log.info("Iniciado a criação de assembleia, {}", createAssemblyRequest);
        CreateAssemblyDTO createAssemblyDTO = objectMapper.convertValue(createAssemblyRequest, CreateAssemblyDTO.class);
        return assemblyService.createAssembly(createAssemblyDTO)
                .doOnSuccess(response -> log.info("Criado assembleia {}", response));
    }

    @GetMapping("/assemblies/{assemblyId}")
    public Mono<AssemblyDTO> getAssemblyById(@PathVariable("assemblyId") String assemblyId) {
        log.info("Buscando assembleia id {}", assemblyId);
        return assemblyService.getAssemblyById(assemblyId)
                .doOnSuccess(response -> log.info("Encontrado assembleia {}", response));
    }

    @PatchMapping("/assemblies/{assemblyId}")
    public Mono<AssemblyDTO> updateAssemblyById(@PathVariable("assemblyId") String assemblyId,
                                                @RequestBody UpdateAssemblyRequest updateAssemblyRequest) {
        log.info("Atualizando assembleia id {} para {}", assemblyId, updateAssemblyRequest);
        return assemblyService.updateAssembly(assemblyId, updateAssemblyRequest)
                .doOnSuccess(response -> log.info("Atualizado assembleia {}", response));
    }

    @PostMapping("/assemblies/{assemblyId}/votes")
    public Mono<VoteDTO> submitVoteAtAssembly(@PathVariable("assemblyId") String assemblyId,
                                              @RequestBody VoteRequest voteRequest) {
        log.info("Criando voto em assembleia");
        return voteService.submitVote(assemblyId, voteRequest)
                .doOnSuccess(response -> log.info("Criado voto {}", response));
    }

    @GetMapping("/assemblies/{assemblyId}/results")
    public Mono<AssemblyResultDTO> getAssemblyResult(@PathVariable("assemblyId") String assemblyId) {
        log.info("Buscando resultado da assembleia {}", assemblyId);
        return assemblyService.getAssemblyResult(assemblyId)
                .doOnSuccess(response -> log.info("Resultado da assembleia {}", response));
    }
}
