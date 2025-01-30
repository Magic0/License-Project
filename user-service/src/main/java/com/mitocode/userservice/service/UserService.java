package com.mitocode.userservice.service;

import com.mitocode.common_models.model.dto.GenericModel;
import com.mitocode.userservice.model.dto.UserDTO;
import com.mitocode.userservice.model.entity.UserEntity;
import com.mitocode.userservice.service.repository.UserRepository;
import com.mitocode.userservice.util.KafkaUtil;
import com.mitocode.userservice.util.UtilMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    public final KafkaUtil kafkaUtil;
    public final UserRepository userRepository;
    public final UtilMapper utilMapper;

    @Value("${server.port}")
    private Integer port;

    public List<UserDTO> getAllUser() {
        List<UserEntity> itUser = userRepository.findAll();
        return itUser.stream().map(userEntities -> {

            UserDTO userDTO = utilMapper.convertEntityToDTO(userEntities);
            userDTO.setPort(port);
            GenericModel<UserEntity> genericModel = new GenericModel<>(UserEntity.builder()
                    .id(userEntities.getId())
                    .name(userEntities.getName())
                    .lastname(userEntities.getLastname())
                    .email(userEntities.getEmail())
                    .username(userEntities.getUsername())
                    .password(userEntities.getPassword())
                    .roles(userEntities.getRoles())
                    .build(), UserEntity.class.getSimpleName());
            kafkaUtil.sendMessageAuditory(genericModel);
            return userDTO;
        }).collect(Collectors.toList());

    }
}
