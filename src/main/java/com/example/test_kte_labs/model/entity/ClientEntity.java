package com.example.test_kte_labs.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    @Column(unique = true, name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "first_discount_rate", nullable = false)
    private Integer firstDiscountRate;

    @Column(name = "second_discount_rate", nullable = false)
    private Integer secondDiscountRate;
}
