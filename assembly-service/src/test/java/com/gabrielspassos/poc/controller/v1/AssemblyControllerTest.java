package com.gabrielspassos.poc.controller.v1;

import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.CreateAssemblyDTO;
import com.gabrielspassos.poc.dto.UpdateAssemblyDTO;
import com.gabrielspassos.poc.service.AssemblyResultService;
import com.gabrielspassos.poc.service.AssemblyService;
import com.gabrielspassos.poc.service.VoteService;
import com.gabrielspassos.poc.stub.dto.AssemblyDTOStub;
import com.gabrielspassos.poc.stub.dto.CreateAssemblyDTOStub;
import com.gabrielspassos.poc.stub.dto.UpdateAssemblyDTOStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static com.gabrielspassos.poc.enumerator.AssemblyStatusEnum.OPEN;
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

    @Test
    public void shouldReturnAssemblyById() throws IOException {
        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/assembly.json")));
        String id = "604387e42df9287972043869";
        LocalDateTime register = LocalDateTime.parse("2021-03-06T10:47:16");
        LocalDateTime update = LocalDateTime.parse("2021-03-06T11:04:05");
        LocalDateTime expiration = LocalDateTime.parse("2021-03-06T11:48:52");
        AssemblyDTO assemblyDTO = AssemblyDTOStub.create(id, "name", "desc", OPEN, register, update, expiration);

        given(assemblyService.getAssemblyById(id))
                .willReturn(Mono.just(assemblyDTO));

        webTestClient.get()
                .uri("/v1/assemblies/{id}", id)
                .header("content-type", "application/json")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(output);
    }

    @Test
    public void shouldReturnAssemblyList() throws IOException {
        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/assemblyList.json")));
        String id = "604387e42df9287972043869";
        LocalDateTime register = LocalDateTime.parse("2021-03-06T10:47:16");
        LocalDateTime update = LocalDateTime.parse("2021-03-06T11:04:05");
        LocalDateTime expiration = LocalDateTime.parse("2021-03-06T11:48:52");
        AssemblyDTO assemblyDTO = AssemblyDTOStub.create(id, "name", "desc", OPEN, register, update, expiration);
        PageRequest page = PageRequest.of(0, 50);

        given(assemblyService.getAssemblies(page))
                .willReturn(Flux.just(assemblyDTO));

        webTestClient.get()
                .uri("/v1/assemblies", id)
                .header("content-type", "application/json")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(output);
    }

    @Test
    public void shouldUpdateAssembly() throws IOException {
        String input = new String(Files.readAllBytes(Paths.get("src/test/resources/json/request/updateAssembly.json")));
        String output = new String(Files.readAllBytes(Paths.get("src/test/resources/json/response/assembly.json")));

        String id = "604387e42df9287972043869";
        LocalDateTime register = LocalDateTime.parse("2021-03-06T10:47:16");
        LocalDateTime update = LocalDateTime.parse("2021-03-06T11:04:05");
        LocalDateTime expiration = LocalDateTime.parse("2021-03-06T11:48:52");
        AssemblyDTO assemblyDTO = AssemblyDTOStub.create(id, "name", "desc", OPEN, register, update, expiration);
        UpdateAssemblyDTO updateAssemblyDTO = UpdateAssemblyDTOStub.create(null);

        given(assemblyService.updateAssembly(id, updateAssemblyDTO))
                .willReturn(Mono.just(assemblyDTO));

        webTestClient.patch()
                .uri("/v1/assemblies/{id}", id)
                .header("content-type", "application/json")
                .bodyValue(input)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(output);
    }

}