package com.example.productapi;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
public class ProductRequest {
    @NotBlank
    @Size(min = 10, max = 10, message = "Code must be exactly 10 characters")
    private String code;
    @NotBlank
    private String name;
    @PositiveOrZero
    private BigDecimal priceEur;
    private boolean isAvailable;
}
