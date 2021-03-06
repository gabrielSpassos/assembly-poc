package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.client.http.CustomerClient;
import com.gabrielspassos.poc.client.http.response.CustomerInfoResponse;
import com.gabrielspassos.poc.client.kafka.VoteProducer;
import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.SubmitVoteDTO;
import com.gabrielspassos.poc.dto.VoteDTO;
import com.gabrielspassos.poc.enumerator.VoteChoiceEnum;
import com.gabrielspassos.poc.exception.AssemblyExpiredException;
import com.gabrielspassos.poc.exception.AssemblyStatusInvalidException;
import com.gabrielspassos.poc.exception.CustomerNotAbleToVoteException;
import com.gabrielspassos.poc.exception.InvalidVoteChoiceException;
import com.gabrielspassos.poc.stub.client.response.CustomerInfoResponseStub;
import com.gabrielspassos.poc.stub.dto.AssemblyDTOStub;
import com.gabrielspassos.poc.stub.dto.CustomerDTOStub;
import com.gabrielspassos.poc.stub.dto.SubmitVoteDTOStub;
import com.gabrielspassos.poc.stub.dto.VoteDTOStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @InjectMocks
    private VoteService voteService;
    @Mock
    private AssemblyService assemblyService;
    @Mock
    private VoteProducer voteProducer;
    @Mock
    private CustomerClient customerClient;

    @Test
    public void shouldSubmitVote() {
        SubmitVoteDTO submitVoteDTO = SubmitVoteDTOStub.create();
        LocalDateTime expiration = LocalDateTime.now().plusDays(1L);
        AssemblyDTO assemblyDTO = AssemblyDTOStub.createWithExpire(expiration);
        CustomerInfoResponse customerInfoResponse = CustomerInfoResponseStub.create("ABLE_TO_VOTE");
        VoteDTO sendVote = VoteDTOStub.create();

        given(assemblyService.getAssemblyById("id"))
                .willReturn(Mono.just(assemblyDTO));
        given(customerClient.getCustomerInfo("80050098012"))
                .willReturn(Mono.just(customerInfoResponse));
        given(voteProducer.sendVoteToTopic("id", sendVote))
                .willReturn(Mono.just(sendVote));

        VoteDTO voteDTO = voteService.submitVote("id", submitVoteDTO).block();

        assertEquals(VoteChoiceEnum.ACCEPTED, voteDTO.getVoteChoice());
        assertEquals("1", voteDTO.getCustomer().getId());
        assertEquals("80050098012", voteDTO.getCustomer().getCpf());
        verify(customerClient).getCustomerInfo("80050098012");
        verify(voteProducer).sendVoteToTopic("id", sendVote);
    }

    @Test
    public void shouldThrowErrorForInvalidVoteChoice() {
        SubmitVoteDTO submitVoteDTO = SubmitVoteDTOStub.create(null, CustomerDTOStub.create());

        InvalidVoteChoiceException error = assertThrows(InvalidVoteChoiceException.class,
                () -> voteService.submitVote("id", submitVoteDTO).block());

        assertEquals(HttpStatus.BAD_REQUEST, error.getHttpStatus());
        assertEquals("Escolha de voto invalida", error.getMessage());
        assertEquals("7", error.getCode());
        verify(customerClient, never()).getCustomerInfo(anyString());
        verify(voteProducer, never()).sendVoteToTopic(anyString(), any());
    }

    @Test
    public void shouldThrowErrorForInvalidAssemblyStatus() {
        SubmitVoteDTO submitVoteDTO = SubmitVoteDTOStub.create();
        AssemblyDTO assemblyDTO = AssemblyDTOStub.createNew();

        given(assemblyService.getAssemblyById("id"))
                .willReturn(Mono.just(assemblyDTO));

        AssemblyStatusInvalidException error = assertThrows(AssemblyStatusInvalidException.class,
                () -> voteService.submitVote("id", submitVoteDTO).block());

        assertEquals(HttpStatus.BAD_REQUEST, error.getHttpStatus());
        assertEquals("Assembleia em status invalido para operacao", error.getMessage());
        assertEquals("3", error.getCode());
        verify(customerClient, never()).getCustomerInfo(anyString());
        verify(voteProducer, never()).sendVoteToTopic(anyString(), any());
    }

    @Test
    public void shouldThrowErrorForAssemblyExpired() {
        SubmitVoteDTO submitVoteDTO = SubmitVoteDTOStub.create();
        LocalDateTime expiration = LocalDateTime.now().minusDays(1L);
        AssemblyDTO assemblyDTO = AssemblyDTOStub.createWithExpire(expiration);

        given(assemblyService.getAssemblyById("id"))
                .willReturn(Mono.just(assemblyDTO));

        AssemblyExpiredException error = assertThrows(AssemblyExpiredException.class,
                () -> voteService.submitVote("id", submitVoteDTO).block());

        assertEquals(HttpStatus.BAD_REQUEST, error.getHttpStatus());
        assertEquals("Assembleia expirada", error.getMessage());
        assertEquals("4", error.getCode());
        verify(customerClient, never()).getCustomerInfo(anyString());
        verify(voteProducer, never()).sendVoteToTopic(anyString(), any());
    }

    @Test
    public void shouldThrowErrorForAssemblyExpiredWithNullExpiration() {
        SubmitVoteDTO submitVoteDTO = SubmitVoteDTOStub.create();
        AssemblyDTO assemblyDTO = AssemblyDTOStub.createWithExpire(null);

        given(assemblyService.getAssemblyById("id"))
                .willReturn(Mono.just(assemblyDTO));

        AssemblyExpiredException error = assertThrows(AssemblyExpiredException.class,
                () -> voteService.submitVote("id", submitVoteDTO).block());

        assertEquals(HttpStatus.BAD_REQUEST, error.getHttpStatus());
        assertEquals("Assembleia expirada", error.getMessage());
        assertEquals("4", error.getCode());
        verify(customerClient, never()).getCustomerInfo(anyString());
        verify(voteProducer, never()).sendVoteToTopic(anyString(), any());
    }

    @Test
    public void shouldThrowErrorForCustomerStatusInvalid() {
        SubmitVoteDTO submitVoteDTO = SubmitVoteDTOStub.create();
        LocalDateTime expiration = LocalDateTime.now().plusDays(1L);
        AssemblyDTO assemblyDTO = AssemblyDTOStub.createWithExpire(expiration);
        CustomerInfoResponse customerInfoResponse = CustomerInfoResponseStub.create("UNABLE_TO_VOTE");

        given(assemblyService.getAssemblyById("id"))
                .willReturn(Mono.just(assemblyDTO));
        given(customerClient.getCustomerInfo("80050098012"))
                .willReturn(Mono.just(customerInfoResponse));

        CustomerNotAbleToVoteException error = assertThrows(CustomerNotAbleToVoteException.class,
                () -> voteService.submitVote("id", submitVoteDTO).block());

        assertEquals(HttpStatus.NOT_FOUND, error.getHttpStatus());
        assertEquals("Usuario nao habilitado para votar", error.getMessage());
        assertEquals("2", error.getCode());
        verify(customerClient).getCustomerInfo("80050098012");
        verify(voteProducer, never()).sendVoteToTopic(anyString(), any());
    }
}