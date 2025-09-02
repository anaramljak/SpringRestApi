package com.example.productapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductRequest product) {
        return new ResponseEntity<>(service.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String code) {
        var product = service.getProduct(code);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
