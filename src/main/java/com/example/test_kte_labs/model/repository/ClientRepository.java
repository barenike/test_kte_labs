package com.example.test_kte_labs.model.repository;

import com.example.test_kte_labs.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {
    @Query("select c from ClientEntity c where c.id = ?1")
    ClientEntity findByClientId(UUID clientID);
}
