package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.AssemblyResultDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.enumerator.AssemblyResultEnum;
import com.gabrielspassos.poc.stub.dto.AssemblyDTOStub;
import com.gabrielspassos.poc.stub.dto.VoteDTOStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AssemblyResultServiceTest {

    @InjectMocks
    private AssemblyResultService assemblyResultService;
    @Mock
    private AssemblyService assemblyService;
    @Mock
    private VoteService voteService;

    @Test
    public void shouldReturnAssemblyResults() {
        AssemblyDTO assemblyDTO = AssemblyDTOStub.create();
        VoteDTO voteDTO = VoteDTOStub.create();

        given(assemblyService.getAssemblyById("id"))
                .willReturn(Mono.just(assemblyDTO));
        given(voteService.getVotesByAssemblyId("id"))
                .willReturn(Flux.just(voteDTO));

        AssemblyResultDTO assemblyResultDTO = assemblyResultService.getAssemblyResult("id").block();

        assertEquals(AssemblyResultEnum.ACCEPTED, assemblyResultDTO.getAssemblyResult());
        assertEquals(1L, assemblyResultDTO.getAcceptedVotesCount());
        assertEquals(0L, assemblyResultDTO.getDeclinedVotesCount());
    }

}