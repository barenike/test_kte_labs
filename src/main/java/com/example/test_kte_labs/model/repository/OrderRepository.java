package com.example.test_kte_labs.model.repository;

import com.example.test_kte_labs.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByClientId(UUID id);

    Optional<OrderEntity> findFirstByOrderByOrderDateDesc();
}
