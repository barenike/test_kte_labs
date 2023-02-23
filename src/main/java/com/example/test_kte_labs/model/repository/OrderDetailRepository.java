package com.example.test_kte_labs.model.repository;

import com.example.test_kte_labs.model.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, UUID> {
}
