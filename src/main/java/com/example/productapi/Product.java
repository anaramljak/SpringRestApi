package com.example.productapi;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, length = 10)
    private String code;
    private String name;
    private BigDecimal priceEur;
    @Transient
    private BigDecimal priceUsd;
    private boolean isAvailable;
}
