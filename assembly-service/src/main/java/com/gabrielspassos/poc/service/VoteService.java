package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.dto.VoteDTOBuilder;
import com.gabrielspassos.poc.builder.entity.VoteEntityBuilder;
import com.gabrielspassos.poc.client.http.CustomerClient;
import com.gabrielspassos.poc.client.kafka.VoteProducer;
import com.gabrielspassos.poc.client.kafka.event.CustomerEvent;
import com.gabrielspassos.poc.client.kafka.event.VoteEvent;
import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.SubmitVoteDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.entity.VoteEntity;
import com.gabrielspassos.poc.enumerator.CustomerStatusEnum;
import com.gabrielspassos.poc.exception.AssemblyExpiredException;
import com.gabrielspassos.poc.exception.AssemblyStatusInvalidException;
import com.gabrielspassos.poc.exception.CustomerInvalidVoteException;
import com.gabrielspassos.poc.exception.CustomerNotAbleToVoteException;
import com.gabrielspassos.poc.exception.InvalidVoteChoiceException;
import com.gabrielspassos.poc.repository.VoteRepository;
import com.gabrielspassos.poc.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.gabrielspassos.poc.enumerator.AssemblyStatusEnum.OPEN;
import static com.gabrielspassos.poc.enumerator.CustomerStatusEnum.ABLE_TO_VOTE;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@AllArgsConstructor
public class VoteService {

    private final AssemblyService assemblyService;
    private final VoteProducer voteProducer;
    private final CustomerClient customerClient;
    private final VoteRepository voteRepository;

    public Mono<VoteDTO> submitVote(String assemblyId, SubmitVoteDTO submitVoteDTO) {
        VoteDTO voteDTO = VoteDTOBuilder.build(assemblyId, submitVoteDTO);

        return Mono.just(submitVoteDTO)
                .filter(submitDTO -> nonNull(submitDTO.getChoice()))
                .switchIfEmpty(Mono.error(new InvalidVoteChoiceException()))
                .flatMap(submitDTO -> assemblyService.getAssemblyById(assemblyId))
                .filter(assemblyDTO -> OPEN.equals(assemblyDTO.getStatus()))
                .switchIfEmpty(Mono.error(new AssemblyStatusInvalidException()))
                .filter(this::isAssemblyNotExpired)
                .switchIfEmpty(Mono.error(new AssemblyExpiredException()))
                .flatMap(assemblyEntity -> validateCustomerStatus(submitVoteDTO.getCustomer().getCpf()))
                .flatMap(customerStatusEnum -> voteProducer.sendVoteToTopic(assemblyId, voteDTO));
    }

    public Mono<VoteDTO> addVoteToAssembly(VoteEvent voteEvent) {
        return getVotesByAssemblyId(voteEvent.getAssemblyId())
                .collectList()
                .filter(votes -> isCustomerVoteValid(votes, voteEvent))
                .switchIfEmpty(Mono.error(new CustomerInvalidVoteException()))
                .map(votes -> VoteEntityBuilder.build(voteEvent))
                .flatMap(this::saveVote);
    }

    public Flux<VoteDTO> getVotesByAssemblyId(String assemblyId) {
        return voteRepository.findByAssemblyId(assemblyId)
                .map(VoteDTOBuilder::build);
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

    private Boolean isCustomerVoteValid(List<VoteDTO> votes, VoteEvent voteEvent) {
        CustomerEvent customer = voteEvent.getCustomer();

        return votes.stream()
                .noneMatch(voteEntity -> voteEntity.getCustomer().getId().equals(customer.getId()));
    }

    private Mono<VoteDTO> saveVote(VoteEntity voteEntity) {
        return voteRepository.save(voteEntity)
                .map(VoteDTOBuilder::build)
                .doOnSuccess(voteDTO -> log.info("Salvo voto {}", voteDTO));
    }

}
