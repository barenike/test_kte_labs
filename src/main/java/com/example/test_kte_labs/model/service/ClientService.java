package com.example.test_kte_labs.model.service;

import com.example.test_kte_labs.exceptions.ClientIsNotFoundException;
import com.example.test_kte_labs.infrastructure.client.ClientCreationRequest;
import com.example.test_kte_labs.model.entity.ClientEntity;
import com.example.test_kte_labs.model.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientEntity> readAll() {
        return clientRepository.findAll();
    }

    public ClientEntity findByClientId(String id) {
        return clientRepository.findByClientId(UUID.fromString(id));
    }

    public ClientEntity getClient(String id) {
        ClientEntity client = clientRepository.findByClientId(UUID.fromString(id));
        if (client == null) {
            throw new ClientIsNotFoundException(id);
        }
        return client;
    }

    public void create(ClientCreationRequest clientCreationRequest) {
        ClientEntity client = new ClientEntity();
        client.setName(clientCreationRequest.getName());
        client.setFirstDiscountRate(clientCreationRequest.getFirstDiscountRate());
        client.setSecondDiscountRate(clientCreationRequest.getSecondDiscountRate());
        clientRepository.save(client);
    }

    public boolean delete(UUID id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void changeDiscountRates(ClientEntity client, Integer firstDiscountRate, Integer secondDiscountRate) {
        client.setFirstDiscountRate(firstDiscountRate);
        client.setSecondDiscountRate(secondDiscountRate);
        clientRepository.save(client);
    }
}
