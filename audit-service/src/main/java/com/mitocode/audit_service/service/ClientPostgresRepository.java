package com.mitocode.audit_service.service;

import com.mitocode.common_models.model.entity.ClientPostgresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientPostgresRepository extends JpaRepository<ClientPostgresEntity, String> {

}
