package com.example.productapi;

import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductService {
    private static final String HNB_API_URL = "https://api.hnb.hr/tecajn/v2?valuta=USD";
    private static final String USD_RATE_KEY = "srednji_tecaj";
    private final ProductRepository repository;
    private final RestTemplate restTemplate;

    public ProductDTO createProduct(ProductRequest request) {
        String code = request.getCode();
        if (repository.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with code " + code + " already exists");
        }

        var product = new Product();
        product.setCode(code);
        product.setName(request.getName());
        product.setPriceEur(request.getPriceEur());
        product.setAvailable(request.isAvailable());
        repository.save(product);

        BigDecimal usdExchangeRate = getUSDExchangeRate();
        BigDecimal priceUsd = product.getPriceEur().multiply(usdExchangeRate);

        return toDTO(product, priceUsd);
    }

    public ProductDTO getProduct(String code) {
        Product product = repository.findByCode(code);

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        BigDecimal usdExchangeRate = getUSDExchangeRate();
        BigDecimal priceUsd = product.getPriceEur().multiply(usdExchangeRate);

        return toDTO(product, priceUsd);
    }

    public List<ProductDTO> getAll() {
        BigDecimal usdExchangeRate = getUSDExchangeRate();
        List<Product> products = repository.findAll();
        return products.stream().map(p -> toDTO(p, p.getPriceEur().multiply(usdExchangeRate))).collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product, BigDecimal priceUsd) {
        return new ProductDTO(product.getId(), product.getCode(), product.getName(), product.getPriceEur(), priceUsd.setScale(2, RoundingMode.HALF_UP), product.isAvailable());
    }

    public BigDecimal getUSDExchangeRate() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(HNB_API_URL, String.class);

            String responseBody = response.getBody();
            if (responseBody == null || responseBody.isEmpty()) {
                throw new RuntimeException("Empty response body");
            }

            JSONArray json = new JSONArray(responseBody);
            JSONObject usdRate = json.getJSONObject(0);
            String rateString = usdRate.getString(USD_RATE_KEY).replace(",", ".");

            return new BigDecimal(rateString);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch or parse USD exchange rate", e);
        }
    }
}
