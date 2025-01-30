package com.mitocode.audit_service.service;

import com.mitocode.common_models.model.entity.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientMongoRepository extends MongoRepository<ClientEntity, String> {
}
