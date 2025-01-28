package com.mitocode.clientservice.service;

import com.mitocode.clientservice.model.dto.ClientDTO;
import com.mitocode.clientservice.model.entity.ClientEntity;
import com.mitocode.clientservice.service.repository.ClientRepository;
import com.mitocode.clientservice.util.UtilMapper;
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
public class ClientService {

    private final ClientRepository clientRepository;
    private final UtilMapper utilMapper;

    @Value("${server.port}")
    private Integer port;

    public ClientDTO saveClient(ClientDTO clientDTO) {
        clientDTO.setPort(port);
        ClientEntity clientEntity = utilMapper.convertDTOtoEntity(clientDTO);
        clientRepository.save(clientEntity);
        clientDTO.setPort(port);
        return clientDTO;
    }

    public List<ClientDTO> getAllClients() {
        List<ClientEntity> itClients = clientRepository.findAll();
        return itClients.stream().map(clientEntities -> {
            ClientDTO clientDTO = utilMapper.convertEntityToDTO(clientEntities);
            clientDTO.setPort(port);
            return clientDTO;
        }).collect(Collectors.toList());

    }

    public ClientDTO getClientById(String id) {
        ClientEntity clientEntity = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        ClientDTO clientResponse = utilMapper.convertEntityToDTO(clientEntity);
        clientResponse.setPort(port);
        return clientResponse;
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
        clientResponse.setPort(port);
        return clientResponse;
    }

}
