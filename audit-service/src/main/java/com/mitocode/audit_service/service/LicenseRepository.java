package com.mitocode.audit_service.service;

import com.mitocode.common_models.model.entity.LicenseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends MongoRepository<LicenseEntity, String> {
}
