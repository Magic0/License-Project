package com.mitocode.clientqueryservice.expose.web;

import com.mitocode.clientqueryservice.model.dto.ClientDTO;
import com.mitocode.clientqueryservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v0/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO savedClient = clientService.saveClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable String id, @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
