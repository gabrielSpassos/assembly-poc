package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.dto.AssemblyResultDTOBuilder;
import com.gabrielspassos.poc.client.kafka.AssemblyResultProducer;
import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.AssemblyResultDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.enumerator.AssemblyResultEnum;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.function.Predicate;

import static com.gabrielspassos.poc.enumerator.VoteChoiceEnum.ACCEPTED;
import static com.gabrielspassos.poc.enumerator.VoteChoiceEnum.DECLINED;

@Slf4j
@Service
@AllArgsConstructor
public class AssemblyResultService {

    private final AssemblyService assemblyService;
    private final VoteService voteService;
    private final AssemblyResultProducer assemblyResultProducer;

    public Mono<AssemblyResultDTO> getAssemblyResult(String assemblyId) {
        return assemblyService.getAssemblyById(assemblyId)
                .flatMap(assemblyDTO -> voteService.getVotesByAssemblyId(assemblyDTO.getId())
                        .collectList()
                        .map(votes -> Tuples.of(assemblyDTO, votes)))
                .map(this::buildAssemblyResult);
    }

    public Mono<AssemblyResultDTO> notifyAssemblyResult(String assemblyId) {
        return getAssemblyResult(assemblyId)
                .flatMap(assemblyResultProducer::sendAssemblyResultToTopic);
    }

    private AssemblyResultDTO buildAssemblyResult(Tuple2<AssemblyDTO, List<VoteDTO>> tuple) {
        AssemblyDTO assemblyDTO = tuple.getT1();
        List<VoteDTO> votes = tuple.getT2();
        Long acceptedVotesCount = votes.stream().filter(filterByVoteChoice(ACCEPTED)).count();
        Long declinedVotesCount = votes.stream().filter(filterByVoteChoice(DECLINED)).count();

        AssemblyResultEnum assemblyResult = AssemblyResultEnum.getResult(acceptedVotesCount, declinedVotesCount);

        return AssemblyResultDTOBuilder.build(assemblyDTO, votes, assemblyResult, acceptedVotesCount, declinedVotesCount);
    }

    private Predicate<VoteDTO> filterByVoteChoice(VoteChoiceEnum choice) {
        return vote -> choice.equals(vote.getVoteChoice());
    }
}
