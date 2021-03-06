package com.gabrielspassos.poc.controller.v1;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.CreateAssemblyDTO;
import com.gabrielspassos.poc.service.AssemblyResultService;
import com.gabrielspassos.poc.service.AssemblyService;
import com.gabrielspassos.poc.service.VoteService;
import com.gabrielspassos.poc.stub.dto.AssemblyDTOStub;
import com.gabrielspassos.poc.stub.dto.CreateAssemblyDTOStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AssemblyControllerTest {

    private WebTestClient webTestClient;
    @Mock
    private AssemblyService assemblyService;
    @Mock
    private VoteService voteService;
    @Mock
    private AssemblyResultService assemblyResultService;
    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient
                .bindToController(new AssemblyController(assemblyService, voteService, assemblyResultService, modelMapper))
                .configureClient()
                .build();
    }

    @Test
    public void shouldCreateAssembly() throws IOException {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/createAssembly.json")));
        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/createAssembly.json")));

        CreateAssemblyDTO createAssemblyDTO = CreateAssemblyDTOStub.create();
        AssemblyDTO assemblyDTO = AssemblyDTOStub.createNew();

        given(assemblyService.createAssembly(createAssemblyDTO))
                .willReturn(Mono.just(assemblyDTO));

        webTestClient.post()
                .uri("/v1/assemblies")
                .header("content-type", "application/json")
                .bodyValue(input)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(output);
    }

}