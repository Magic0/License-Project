package com.mitocode.clientqueryservice.expose.web;

import com.mitocode.clientqueryservice.model.dto.ClientDTO;
import com.mitocode.clientqueryservice.service.ClientQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v0/clients")
@RequiredArgsConstructor
public class ClientQueryController {

    private final ClientQueryService clientQueryService;


    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientQueryService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable String id) {
        ClientDTO client = clientQueryService.getClientById(id);
        return ResponseEntity.ok(client);
    }

}
