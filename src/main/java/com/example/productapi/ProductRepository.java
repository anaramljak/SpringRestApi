package com.example.productapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
    Product findByCode(String code);
    List<Product> findAll();
    boolean existsByCode(String code);
}