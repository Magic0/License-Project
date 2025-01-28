package com.mitocode.userservice.repository;

//import com.mitocode.microservices.common_models.model.entity.UserEntity;

import com.mitocode.userservice.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "user")
public interface UserRepository extends MongoRepository<UserEntity, String> {


    @RestResource(path = "correito")
    UserEntity getAllByEmail(String email);

    @RestResource(path = "apellido")
    List<UserEntity> getAllByLastnameContains(String lastname);

    @RestResource(path = "multiple")

    List<UserEntity> getAllByLastnameContainsAndEmailContains(String lastname, String email);

}
