package com.gabrielspassos.poc.service;

import com.gabrielspassos.poc.builder.dto.AssemblyDTOBuilder;
import com.gabrielspassos.poc.builder.entity.AssemblyEntityBuilder;
import com.gabrielspassos.poc.config.AssemblyConfig;
import com.gabrielspassos.poc.dto.AssemblyDTO;
import com.gabrielspassos.poc.dto.CreateAssemblyDTO;
import com.gabrielspassos.poc.dto.UpdateAssemblyDTO;
import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import com.gabrielspassos.poc.exception.AssemblyStatusInvalidException;
import com.gabrielspassos.poc.exception.NotFoundAssemblyException;
import com.gabrielspassos.poc.mapper.AssemblyEntityMapper;
import com.gabrielspassos.poc.repository.AssemblyRepository;
import com.gabrielspassos.poc.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static com.gabrielspassos.poc.enumerator.AssemblyStatusEnum.CLOSED;
import static com.gabrielspassos.poc.enumerator.AssemblyStatusEnum.EXPIRED;
import static com.gabrielspassos.poc.enumerator.AssemblyStatusEnum.OPEN;

@Slf4j
@Service
@AllArgsConstructor
public class AssemblyService {

    private final AssemblyConfig assemblyConfig;
    private final AssemblyRepository assemblyRepository;

    public Mono<AssemblyDTO> createAssembly(CreateAssemblyDTO createAssemblyDTO) {
        AssemblyEntity assemblyEntity = AssemblyEntityBuilder.build(createAssemblyDTO);
        return saveAssembly(assemblyEntity);
    }

    public Mono<AssemblyDTO> updateAssembly(String assemblyId, UpdateAssemblyDTO updateAssemblyDTO) {
        return getAssemblyById(assemblyId)
                .filter(dto -> assemblyConfig.getStatusAbleToUpdate().contains(dto.getStatus()))
                .switchIfEmpty(Mono.error(new AssemblyStatusInvalidException()))
                .map(AssemblyEntityBuilder::build)
                .map(assemblyEntity -> AssemblyEntityMapper.map(
                        assemblyEntity, updateAssemblyDTO, assemblyConfig.getAssemblyDefaultExpirationMinutes()))
                .flatMap(this::saveAssembly);
    }

    public Mono<AssemblyDTO> getAssemblyById(String assemblyId) {
        return assemblyRepository.findById(assemblyId)
                .switchIfEmpty(Mono.error(new NotFoundAssemblyException()))
                .map(AssemblyDTOBuilder::build);
    }

    public Flux<AssemblyDTO> getAssemblies(Pageable page) {
        return assemblyRepository.findAllBy(page)
                .map(AssemblyDTOBuilder::build);
    }

    public Flux<AssemblyDTO> expireAssemblies() {
        List<AssemblyStatusEnum> status = List.of(OPEN, CLOSED);
        LocalDateTime now = DateTimeUtil.now();
        LocalDateTime fromDate = now.with(LocalTime.MIN);
        LocalDateTime toDate = now.with(LocalTime.MAX);

        return assemblyRepository.findByStatusInAndRegisterDateTimeBetween(status, fromDate, toDate)
                .filter(isAssemblyExpired(now))
                .map(assemblyEntity -> AssemblyEntityMapper.map(assemblyEntity, EXPIRED))
                .flatMap(this::saveAssembly);
    }

    private Mono<AssemblyDTO> saveAssembly(AssemblyEntity assemblyEntity) {
        return assemblyRepository.save(assemblyEntity)
                .map(AssemblyDTOBuilder::build)
                .doOnSuccess(entity -> log.info("Salvo assembleia {}", entity));
    }

    private Predicate<AssemblyEntity> isAssemblyExpired(LocalDateTime now) {
        return assembly -> Objects.nonNull(assembly.getExpirationDateTime())
                && now.isAfter(assembly.getExpirationDateTime());
    }

}
