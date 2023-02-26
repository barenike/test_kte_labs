package com.example.test_kte_labs.controller.rest;

import com.example.test_kte_labs.infrastructure.client.ChangeDiscountRatesRequest;
import com.example.test_kte_labs.infrastructure.client.ClientCreationRequest;
import com.example.test_kte_labs.model.entity.ClientEntity;
import com.example.test_kte_labs.model.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client")
    public ResponseEntity<?> create(@RequestBody @Valid ClientCreationRequest clientCreationRequest) {
        clientService.create(clientCreationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final boolean isDeleted = clientService.delete(id);
        return isDeleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/clients")
    public ResponseEntity<?> getClients() {
        final List<ClientEntity> clients = clientService.readAll();
        return clients != null && !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("client/discount_rates")
    public ResponseEntity<?> changeDiscountRates(@RequestBody @Valid ChangeDiscountRatesRequest changeDiscountRatesRequest) {
        String id = changeDiscountRatesRequest.getId();
        ClientEntity client = clientService.getClient(id);
        clientService.changeDiscountRates(client,
                changeDiscountRatesRequest.getFirstDiscountRate(),
                changeDiscountRatesRequest.getSecondDiscountRate());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
