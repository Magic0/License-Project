package com.mitocode.clientqueryservice.service;

import com.mitocode.clientqueryservice.model.dto.ClientDTO;
import com.mitocode.clientqueryservice.model.entity.ClientEntity;
import com.mitocode.clientqueryservice.service.repository.ClientRepository;
import com.mitocode.clientqueryservice.util.KafkaUtil;
import com.mitocode.clientqueryservice.util.UtilMapper;
import com.mitocode.common_models.model.dto.GenericModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final KafkaUtil kafkaUtil;
    private final ClientRepository clientRepository;
    private final UtilMapper utilMapper;

    @Value("${server.port}")
    private Integer port;

    public ClientDTO saveClient(ClientDTO clientDTO) {
        clientDTO.setPort(port);
        ClientEntity clientPostgresEntity = utilMapper.convertDTOtoEntity(clientDTO);
        clientRepository.save(clientPostgresEntity);
        clientDTO.setPort(port);
        GenericModel<ClientEntity> genericModel = new GenericModel<>(ClientEntity.builder()
                .DNI(clientPostgresEntity.getDNI())
                .name(clientPostgresEntity.getName())
                .lastname(clientPostgresEntity.getLastname())
                .email(clientPostgresEntity.getEmail())
                .birthday(clientPostgresEntity.getBirthday())
                .build(), ClientEntity.class.getSimpleName());
        kafkaUtil.sendMessageAuditory(genericModel);
        kafkaUtil.sendMessagePersistence(genericModel);
        return clientDTO;
    }

    public void deleteClient(String id) {
        clientRepository.deleteById(id);
    }

    public ClientDTO updateClient(String clientId, ClientDTO clientDTO) {
        ClientEntity clientVerify = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        utilMapper.copyNonNullProperties(clientDTO, clientVerify);
        ClientEntity clientVerify2 = clientRepository.save(clientVerify);
        ClientDTO clientResponse = utilMapper.convertEntityToDTO(clientVerify2);
        GenericModel<ClientEntity> genericModel = new GenericModel<>(ClientEntity.builder()
                .DNI(clientVerify2.getDNI())
                .name(clientVerify2.getName())
                .lastname(clientVerify2.getLastname())
                .email(clientVerify2.getEmail())
                .birthday(clientVerify2.getBirthday())
                .build(), ClientEntity.class.getSimpleName());
        clientResponse.setPort(port);
        kafkaUtil.sendMessageAuditory(genericModel);
        kafkaUtil.sendMessagePersistence(genericModel);
        return clientResponse;
    }


}
