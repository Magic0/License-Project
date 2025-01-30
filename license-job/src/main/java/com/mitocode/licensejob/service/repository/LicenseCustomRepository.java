package com.mitocode.licensejob.service.repository;

import com.mitocode.licensejob.model.entity.LicenseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LicenseCustomRepository {
    private final MongoTemplate mongoTemplate;

    public List<LicenseEntity> getAllByExpirationDateBeforeAndIsActive(LocalDate expirationDate, boolean isActive) {
        // Convertir LocalDate a LocalDateTime en la zona horaria UTC
        LocalDateTime startOfDay = expirationDate.atStartOfDay();
        Instant expirationInstant = startOfDay.toInstant(ZoneOffset.UTC);

        Query query = new Query();
        query.addCriteria(Criteria.where("expirationDate").lt(expirationInstant));
        query.addCriteria(Criteria.where("isActive").is(isActive)); // Solo licencias activas

        return mongoTemplate.find(query, LicenseEntity.class);
    }
}
