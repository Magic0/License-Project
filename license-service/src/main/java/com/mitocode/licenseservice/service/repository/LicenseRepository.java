package com.mitocode.licenseservice.service.repository;

import com.mitocode.licenseservice.model.dto.CategoryReportDTO;
import com.mitocode.licenseservice.model.entity.LicenseEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends MongoRepository<LicenseEntity, String> {


    List<LicenseEntity> getAllByLicenseCategory(String licenseCategory);

    List<LicenseEntity> getAllByLicenseType(String licenseType);

    List<LicenseEntity> getAllByIsActive(Boolean isActive);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$licenseCategory', total: { $sum: 1 } } }"
    })
    List<CategoryReportDTO> getLicenseCountByCategory();
}
