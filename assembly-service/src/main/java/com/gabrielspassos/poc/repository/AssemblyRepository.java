package com.gabrielspassos.poc.repository;

import com.gabrielspassos.poc.entity.AssemblyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AssemblyRepository extends ReactiveMongoRepository<AssemblyEntity, String> {

    Flux<AssemblyEntity> findAllBy(Pageable page);

}
