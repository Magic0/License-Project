package com.mitocode.clientqueryservice.service.repository;

import com.mitocode.clientqueryservice.model.entity.ClientPostgresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientQueryRepository extends JpaRepository<ClientPostgresEntity, String> {

}
