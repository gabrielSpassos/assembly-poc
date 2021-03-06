package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.config.AssemblyConfig;
import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.CreateAssemblyDTO;
import com.gabrielspassos.poc.dto.UpdateAssemblyDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import com.gabrielspassos.poc.exception.NotFoundAssemblyException;
import com.gabrielspassos.poc.repository.AssemblyRepository;
import com.gabrielspassos.poc.stub.dto.CreateAssemblyDTOStub;
import com.gabrielspassos.poc.stub.dto.UpdateAssemblyDTOStub;
import com.gabrielspassos.poc.stub.entity.AssemblyEntityStub;
import com.gabrielspassos.poc.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.gabrielspassos.poc.enumerator.AssemblyStatusEnum.CLOSED;
import static com.gabrielspassos.poc.enumerator.AssemblyStatusEnum.EXPIRED;
import static com.gabrielspassos.poc.enumerator.AssemblyStatusEnum.OPEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AssemblyServiceTest {

    @InjectMocks
    private AssemblyService assemblyService;
    @Mock
    private VoteService voteService;
    @Mock
    private AssemblyConfig assemblyConfig;
    @Mock
    private AssemblyRepository assemblyRepository;

    @Test
    public void shouldReturnAssemblies() {
        PageRequest pageRequest = PageRequest.of(0, 50);
        AssemblyEntity assemblyEntity = AssemblyEntityStub.create();

        given(assemblyRepository.findAllBy(pageRequest))
                .willReturn(Flux.just(assemblyEntity));

        List<AssemblyDTO> assemblyDTOList = assemblyService.getAssemblies(pageRequest).collectList().block();

        assertNotNull(assemblyDTOList);
        assertEquals("id", assemblyDTOList.get(0).getId());
        assertEquals(AssemblyStatusEnum.CLOSED, assemblyDTOList.get(0).getStatus());
    }

    @Test
    public void shouldGetAssemblyById() {
        AssemblyEntity assemblyEntity = AssemblyEntityStub.create();

        given(assemblyRepository.findById("id"))
                .willReturn(Mono.just(assemblyEntity));

        AssemblyDTO assemblyDTO = assemblyService.getAssemblyById("id").block();

        assertEquals("id", assemblyDTO.getId());
        assertEquals("name", assemblyDTO.getName());
    }

    @Test
    public void shouldThrowErrorForNotFoundAssemblyById() {
        given(assemblyRepository.findById("id"))
                .willReturn(Mono.empty());

        NotFoundAssemblyException error = assertThrows(NotFoundAssemblyException.class,
                () -> assemblyService.getAssemblyById("id").block()
        );

        assertEquals(HttpStatus.NOT_FOUND, error.getHttpStatus());
        assertEquals("Nao encontrado assembleia", error.getMessage());
        assertEquals("1", error.getCode());
    }

    @Test
    public void shouldCreateAssembly() {
        CreateAssemblyDTO createAssemblyDTO = CreateAssemblyDTOStub.create();
        AssemblyEntity assemblyEntity = AssemblyEntityStub.createNew();
        ArgumentCaptor<AssemblyEntity> argumentCaptor = ArgumentCaptor.forClass(AssemblyEntity.class);

        given(assemblyRepository.save(argumentCaptor.capture()))
                .willReturn(Mono.just(assemblyEntity));

        AssemblyDTO assemblyDTO = assemblyService.createAssembly(createAssemblyDTO).block();

        AssemblyEntity value = argumentCaptor.getValue();

        verify(assemblyRepository).save(any());
        assertEquals("id", assemblyDTO.getId());
        assertEquals(AssemblyStatusEnum.CLOSED, value.getStatus());
        assertEquals("name", value.getName());
        assertEquals("desc", value.getDescription());
        assertNotNull(value.getRegisterDateTime());
        assertNull(value.getUpdateDateTime());
        assertNull(value.getExpirationDateTime());
    }

    @Test
    public void shouldUpdateAssemblyWithoutExpiration() {
        UpdateAssemblyDTO updateAssemblyDTO = UpdateAssemblyDTOStub.create(null);
        AssemblyEntity old = AssemblyEntityStub.createNew();
        AssemblyEntity updated = AssemblyEntityStub.createUpdate();
        ArgumentCaptor<AssemblyEntity> argumentCaptor = ArgumentCaptor.forClass(AssemblyEntity.class);

        given(assemblyRepository.findById("id"))
                .willReturn(Mono.just(old));
        given(assemblyConfig.getAssemblyDefaultExpirationMinutes())
                .willReturn(1L);
        given(assemblyRepository.save(argumentCaptor.capture()))
                .willReturn(Mono.just(updated));

        AssemblyDTO assemblyDTO = assemblyService.updateAssembly("id", updateAssemblyDTO).block();

        AssemblyEntity value = argumentCaptor.getValue();

        verify(assemblyRepository).findById("id");
        verify(assemblyRepository).save(any());
        assertEquals(AssemblyStatusEnum.OPEN, assemblyDTO.getStatus());
        assertEquals(AssemblyStatusEnum.OPEN, value.getStatus());
        assertNotNull(value.getUpdateDateTime());
        assertNotNull(value.getExpirationDateTime());
    }

    @Test
    public void shouldUpdateAssemblyWithExpiration() {
        LocalDateTime expiration = LocalDateTime.parse("2021-03-10T10:15:30");
        UpdateAssemblyDTO updateAssemblyDTO = UpdateAssemblyDTOStub.create(expiration);
        AssemblyEntity old = AssemblyEntityStub.createNew();
        AssemblyEntity updated = AssemblyEntityStub.createUpdate();
        ArgumentCaptor<AssemblyEntity> argumentCaptor = ArgumentCaptor.forClass(AssemblyEntity.class);

        given(assemblyRepository.findById("id"))
                .willReturn(Mono.just(old));
        given(assemblyConfig.getAssemblyDefaultExpirationMinutes())
                .willReturn(1L);
        given(assemblyRepository.save(argumentCaptor.capture()))
                .willReturn(Mono.just(updated));

        AssemblyDTO assemblyDTO = assemblyService.updateAssembly("id", updateAssemblyDTO).block();

        AssemblyEntity value = argumentCaptor.getValue();

        verify(assemblyRepository).findById("id");
        verify(assemblyRepository).save(any());
        assertEquals(AssemblyStatusEnum.OPEN, assemblyDTO.getStatus());
        assertEquals(AssemblyStatusEnum.OPEN, value.getStatus());
        assertNotNull(value.getUpdateDateTime());
        assertEquals(expiration, value.getExpirationDateTime());
    }

    @Test
    public void shouldExpireAssemblies() {
        ArgumentCaptor<AssemblyEntity> argumentCaptor = ArgumentCaptor.forClass(AssemblyEntity.class);
        LocalDateTime expiration = LocalDateTime.parse("2021-03-04T10:15:30");
        AssemblyEntity toExpire = AssemblyEntityStub.createToExpire(expiration);
        AssemblyEntity expired = AssemblyEntityStub.createExpired(expiration);
        List<AssemblyStatusEnum> status = List.of(OPEN, CLOSED);
        LocalDateTime now = DateTimeUtil.now();
        LocalDateTime fromDate = now.with(LocalTime.MIN);
        LocalDateTime toDate = now.with(LocalTime.MAX);

        given(assemblyRepository.findByStatusInAndRegisterDateTimeBetween(status, fromDate, toDate))
                .willReturn(Flux.just(toExpire));
        given(assemblyRepository.save(argumentCaptor.capture()))
                .willReturn(Mono.just(expired));

        List<AssemblyDTO> assemblyDTOList = assemblyService.expireAssemblies().collectList().block();

        AssemblyEntity value = argumentCaptor.getValue();

        verify(assemblyRepository).findByStatusInAndRegisterDateTimeBetween(status, fromDate, toDate);
        assertEquals(1, assemblyDTOList.size());
        assertEquals(EXPIRED, assemblyDTOList.get(0).getStatus());
        assertEquals(EXPIRED, value.getStatus());
    }

    @Test
    public void shouldNotExpireAssemblies() {
        List<AssemblyStatusEnum> status = List.of(OPEN, CLOSED);
        LocalDateTime now = DateTimeUtil.now();
        LocalDateTime fromDate = now.with(LocalTime.MIN);
        LocalDateTime toDate = now.with(LocalTime.MAX);
        AssemblyEntity toExpire = AssemblyEntityStub.createToExpire(now.plusDays(2L));

        given(assemblyRepository.findByStatusInAndRegisterDateTimeBetween(status, fromDate, toDate))
                .willReturn(Flux.just(toExpire));

        List<AssemblyDTO> assemblyDTOList = assemblyService.expireAssemblies().collectList().block();

        verify(assemblyRepository).findByStatusInAndRegisterDateTimeBetween(status, fromDate, toDate);
        verify(assemblyRepository, never()).save(any());
        assertEquals(0, assemblyDTOList.size());
    }
}