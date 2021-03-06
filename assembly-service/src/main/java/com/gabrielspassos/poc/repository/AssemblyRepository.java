package com.gabrielspassos.poc.repository;

import com.gabrielspassos.poc.entity.AssemblyEntity;
import com.gabrielspassos.poc.enumerator.AssemblyStatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssemblyRepository extends ReactiveMongoRepository<AssemblyEntity, String> {

    Flux<AssemblyEntity> findAllBy(Pageable page);

    Flux<AssemblyEntity> findByStatusInAndRegisterDateTimeBetween(List<AssemblyStatusEnum> status,
                                                                  LocalDateTime fromDateTime,
                                                                  LocalDateTime toDateTime);

}
