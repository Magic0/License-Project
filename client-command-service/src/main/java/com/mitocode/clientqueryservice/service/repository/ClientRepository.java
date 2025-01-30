package com.mitocode.clientqueryservice.service.repository;

import com.mitocode.clientqueryservice.model.entity.ClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<ClientEntity, String> {

}
