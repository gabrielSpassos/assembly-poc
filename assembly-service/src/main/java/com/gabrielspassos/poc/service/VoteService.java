package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.dto.AssemblyDTOBuilder;
import com.gabrielspassos.poc.builder.dto.VoteDTOBuilder;
import com.gabrielspassos.poc.builder.entity.AssemblyEntityBuilder;
import com.gabrielspassos.poc.client.http.CustomerClient;
import com.gabrielspassos.poc.client.kafka.VoteProducer;
import com.gabrielspassos.poc.client.kafka.event.CustomerEvent;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.controller.v1.request.VoteRequest;
import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;
import com.gabrielspassos.poc.exception.AssemblyExpiredException;
import com.gabrielspassos.poc.exception.AssemblyStatusInvalidException;
import com.gabrielspassos.poc.exception.CustomerInvalidVoteException;
import com.gabrielspassos.poc.exception.CustomerNotAbleToVoteException;
import com.gabrielspassos.poc.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.gabrielspassos.poc.enumerator.AssemblyStatusEnum.OPEN;
import static com.gabrielspassos.poc.enumerator.CustomerStatusEnum.ABLE_TO_VOTE;

@Slf4j
@Service
@AllArgsConstructor
public class VoteService {

    private final AssemblyService assemblyService;
    private final VoteProducer voteProducer;
    private final CustomerClient customerClient;

    public Mono<VoteDTO> submitVote(String assemblyId, VoteRequest voteRequest) {
        VoteDTO voteDTO = VoteDTOBuilder.build(voteRequest);

        return assemblyService.getAssemblyById(assemblyId)
                .filter(assemblyDTO -> OPEN.equals(assemblyDTO.getStatus()))
                .switchIfEmpty(Mono.error(new AssemblyStatusInvalidException()))
                .filter(this::isAssemblyNotExpired)
                .switchIfEmpty(Mono.error(new AssemblyExpiredException()))
                .flatMap(assemblyEntity -> validateCustomerStatus(voteRequest.getCustomer().getCpf()))
                .flatMap(customerStatusEnum -> voteProducer.sendVoteToTopic(assemblyId, voteDTO));
    }

    public Flux<VoteDTO> addVoteToAssembly(VoteEvent voteEvent) {
        return assemblyService.getAssemblyById(voteEvent.getAssemblyId())
                .filter(assemblyDTO -> isCustomerVoteValid(assemblyDTO, voteEvent))
                .switchIfEmpty(Mono.error(new CustomerInvalidVoteException()))
                .map(assemblyDTO -> addVoteToAssembly(assemblyDTO, voteEvent))
                .map(AssemblyEntityBuilder::build)
                .flatMap(assemblyService::saveAssembly)
                .flatMapIterable(AssemblyDTO::getVotes);
    }

    private Boolean isAssemblyNotExpired(AssemblyDTO assemblyDTO) {
        if (Objects.isNull(assemblyDTO.getExpirationDateTime())) {
            return Boolean.FALSE;
        }

        LocalDateTime now = DateTimeUtil.now();
        return !now.isAfter(assemblyDTO.getExpirationDateTime());
    }

    private Mono<CustomerStatusEnum> validateCustomerStatus(String customerCpf) {
        return customerClient.getCustomerInfo(customerCpf)
                .map(customerInfoResponse -> CustomerStatusEnum.getCustomerStatus(customerInfoResponse.getStatus()))
                .filter(ABLE_TO_VOTE::equals)
                .switchIfEmpty(Mono.error(new CustomerNotAbleToVoteException()));
    }

    private Boolean isCustomerVoteValid(AssemblyDTO assemblyDTO, VoteEvent voteEvent) {
        CustomerEvent customer = voteEvent.getCustomer();

        return assemblyDTO.getVotes().stream()
                .noneMatch(voteEntity -> voteEntity.getCustomer().getId().equals(customer.getId()));
    }

    private AssemblyDTO addVoteToAssembly(AssemblyDTO assemblyDTO, VoteEvent voteEvent) {
        VoteDTO voteDTO = VoteDTOBuilder.build(voteEvent);

        assemblyDTO.getVotes().add(voteDTO);

        return assemblyDTO;
    }

}
