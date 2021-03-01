package com.gabrielspassos.poc.repository;

import com.gabrielspassos.poc.entity.AssemblyEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssemblyRepository extends ReactiveMongoRepository<AssemblyEntity, String> {
}
