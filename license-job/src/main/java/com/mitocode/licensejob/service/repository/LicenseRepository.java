package com.mitocode.licensejob.service.repository;

import com.mitocode.licensejob.model.entity.LicenseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends MongoRepository<LicenseEntity, String> {
}