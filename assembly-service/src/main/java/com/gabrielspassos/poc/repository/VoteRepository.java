package com.gabrielspassos.poc.repository;

import com.gabrielspassos.poc.entity.VoteEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface VoteRepository extends ReactiveMongoRepository<VoteEntity, String> {

    Flux<VoteEntity> findByAssemblyId(String assemblyId);
}
