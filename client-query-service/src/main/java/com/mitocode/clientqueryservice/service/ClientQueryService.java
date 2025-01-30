package com.mitocode.clientqueryservice.service;

import com.mitocode.clientqueryservice.model.dto.ClientDTO;
import com.mitocode.clientqueryservice.model.entity.ClientPostgresEntity;
import com.mitocode.clientqueryservice.service.repository.ClientQueryRepository;
import com.mitocode.clientqueryservice.util.UtilMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientQueryService {

    private final ClientQueryRepository clientQueryRepository;
    private final UtilMapper utilMapper;

    @Value("${server.port}")
    private Integer port;

    public List<ClientDTO> getAllClients() {
        List<ClientPostgresEntity> itClients = clientQueryRepository.findAll();
        return itClients.stream().map(clientEntities -> {
            ClientDTO clientDTO = utilMapper.convertEntityToDTO(clientEntities);
            clientDTO.setPort(port);
            return clientDTO;
        }).collect(Collectors.toList());

    }

    public ClientDTO getClientById(String id) {
        ClientPostgresEntity clientEntity = clientQueryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        ClientDTO clientResponse = utilMapper.convertEntityToDTO(clientEntity);
        clientResponse.setPort(port);
        return clientResponse;
    }

}
