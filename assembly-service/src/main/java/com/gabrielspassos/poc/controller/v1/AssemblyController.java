package com.gabrielspassos.poc.controller.v1;

import com.gabrielspassos.poc.builder.dto.SubmitVoteDTOBuilder;
import com.gabrielspassos.poc.controller.v1.request.CreateAssemblyRequest;
import com.gabrielspassos.poc.controller.v1.request.UpdateAssemblyRequest;
import com.gabrielspassos.poc.controller.v1.request.VoteRequest;
import com.gabrielspassos.poc.controller.v1.response.AssemblyResponse;
import com.gabrielspassos.poc.controller.v1.response.AssemblyResultResponse;
import com.gabrielspassos.poc.controller.v1.response.VoteResponse;
import com.gabrielspassos.poc.dto.CreateAssemblyDTO;
import com.gabrielspassos.poc.dto.SubmitVoteDTO;
import com.gabrielspassos.poc.dto.UpdateAssemblyDTO;
import com.gabrielspassos.poc.service.AssemblyService;
import com.gabrielspassos.poc.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @GetMapping("/assemblies")
    public Flux<AssemblyResponse> getAssemblies(@RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page,
                                                @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size) {
        log.info("Iniciado busca de assembleias na página {} e tamanho {}", page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        return assemblyService.getAssemblies(pageRequest)
                .map(dto -> modelMapper.map(dto, AssemblyResponse.class))
                .doOnComplete(() -> log.info("Finalizado consulta de assembleias"));
    }

    @PostMapping("/assemblies")
    public Mono<AssemblyResponse> createAssembly(@RequestBody CreateAssemblyRequest createAssemblyRequest) {
        log.info("Iniciado a criação de assembleia, {}", createAssemblyRequest);
        CreateAssemblyDTO createAssemblyDTO = modelMapper.map(createAssemblyRequest, CreateAssemblyDTO.class);
        return assemblyService.createAssembly(createAssemblyDTO)
                .map(dto -> modelMapper.map(dto, AssemblyResponse.class))
                .doOnSuccess(response -> log.info("Criado assembleia {}", response));
    }

    @GetMapping("/assemblies/{assemblyId}")
    public Mono<AssemblyResponse> getAssemblyById(@PathVariable("assemblyId") String assemblyId) {
        log.info("Buscando assembleia id {}", assemblyId);
        return assemblyService.getAssemblyById(assemblyId)
                .map(dto -> modelMapper.map(dto, AssemblyResponse.class))
                .doOnSuccess(response -> log.info("Encontrado assembleia {}", response));
    }

    @PatchMapping("/assemblies/{assemblyId}")
    public Mono<AssemblyResponse> updateAssemblyById(@PathVariable("assemblyId") String assemblyId,
                                                     @RequestBody UpdateAssemblyRequest updateAssemblyRequest) {
        log.info("Atualizando assembleia id {} para {}", assemblyId, updateAssemblyRequest);
        UpdateAssemblyDTO updateAssemblyDTO = modelMapper.map(updateAssemblyRequest, UpdateAssemblyDTO.class);
        return assemblyService.updateAssembly(assemblyId, updateAssemblyDTO)
                .map(dto -> modelMapper.map(dto, AssemblyResponse.class))
                .doOnSuccess(response -> log.info("Atualizado assembleia {}", response));
    }

    @PostMapping("/assemblies/{assemblyId}/votes")
    public Mono<VoteResponse> submitVoteAtAssembly(@PathVariable("assemblyId") String assemblyId,
                                                   @RequestBody VoteRequest voteRequest) {
        log.info("Criando voto em assembleia");
        SubmitVoteDTO submitVoteDTO = SubmitVoteDTOBuilder.build(voteRequest);
        return voteService.submitVote(assemblyId, submitVoteDTO)
                .map(dto -> modelMapper.map(dto, VoteResponse.class))
                .doOnSuccess(response -> log.info("Criado voto {}", response));
    }

    @GetMapping("/assemblies/{assemblyId}/results")
    public Mono<AssemblyResultResponse> getAssemblyResult(@PathVariable("assemblyId") String assemblyId) {
        log.info("Buscando resultado da assembleia {}", assemblyId);
        return assemblyService.getAssemblyResult(assemblyId)
                .map(dto -> modelMapper.map(dto, AssemblyResultResponse.class))
                .doOnSuccess(response -> log.info("Resultado da assembleia {}", response));
    }
}
