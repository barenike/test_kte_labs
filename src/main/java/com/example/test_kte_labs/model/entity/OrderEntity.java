package com.example.test_kte_labs.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @Column(unique = true, name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Column(name = "cheque_number", nullable = false)
    private Integer cheque_number;
}
