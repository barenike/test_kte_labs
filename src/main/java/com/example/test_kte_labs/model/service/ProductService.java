package com.example.test_kte_labs.model.service;

import com.example.test_kte_labs.exceptions.ProductIsNotFoundException;
import com.example.test_kte_labs.infrastructure.product.ProductCreationRequest;
import com.example.test_kte_labs.infrastructure.product.ProductFinalPriceRequest;
import com.example.test_kte_labs.infrastructure.product.ProductResponse;
import com.example.test_kte_labs.model.entity.ClientEntity;
import com.example.test_kte_labs.model.entity.ProductEntity;
import com.example.test_kte_labs.model.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ClientService clientService;

    public ProductService(ProductRepository productRepository, ClientService clientService) {
        this.productRepository = productRepository;
        this.clientService = clientService;
    }

    public void create(ProductCreationRequest productCreationRequest) {
        ProductEntity product = new ProductEntity();
        product.setName(productCreationRequest.getName());
        product.setPrice(productCreationRequest.getPrice());
        product.setDescription(productCreationRequest.getDescription());
        product.setDiscountRate(0);
        productRepository.save(product);
    }

    public boolean delete(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductEntity getProduct(String id) {
        ProductEntity product = productRepository.findByProductId(UUID.fromString(id));
        if (product == null) {
            throw new ProductIsNotFoundException(id);
        }
        return product;
    }

    public List<ProductResponse> getProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream().map(product -> new ProductResponse(
                product.getId().toString(),
                product.getName(),
                product.getPrice()
        )).collect(Collectors.toList());
    }

    public Double getProductFinalPrice(ProductFinalPriceRequest productFinalPriceRequest) {
        String clientId = productFinalPriceRequest.getClientId();
        String productId = productFinalPriceRequest.getProductId();
        Integer quantity = productFinalPriceRequest.getQuantity();
        ProductEntity product = getProduct(productId);

        int totalDiscountRate = getTotalDiscountRate(clientId, productId, quantity);

        double finalPrice = product.getPrice() * quantity;
        finalPrice = finalPrice * (100 - totalDiscountRate) / 100;

        DecimalFormat f = new DecimalFormat("##.00");
        finalPrice = Double.parseDouble(f.format(finalPrice));

        return finalPrice;
    }

    public Integer getTotalDiscountRate(String clientId, String productId, Integer quantity) {
        ProductEntity product = getProduct(productId);
        ClientEntity client = clientService.getClient(clientId);

        int totalDiscountRate;
        Integer productDiscountRate = product.getDiscountRate();
        Integer clientDiscountRate;

        if (quantity >= 5 && client.getSecondDiscountRate() != 0) {
            clientDiscountRate = client.getSecondDiscountRate();
        } else {
            clientDiscountRate = client.getFirstDiscountRate();
        }

        totalDiscountRate = productDiscountRate + clientDiscountRate;

        if (totalDiscountRate > 18) {
            totalDiscountRate = 18;
        }

        return totalDiscountRate;
    }
}
