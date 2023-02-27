package com.example.test_kte_labs.model.service.task;

import com.example.test_kte_labs.model.entity.ProductEntity;
import com.example.test_kte_labs.model.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ProductDiscountRateService {
    private final ProductService productService;

    public ProductDiscountRateService(ProductService productService) {
        this.productService = productService;
    }

    public void executeProductDiscountRateTask() {
        List<ProductEntity> productsWithNotNullDiscountRate = productService.getProductListWithNonZeroDiscountRate();
        productsWithNotNullDiscountRate.forEach(productService::deleteDiscountRate);

        List<ProductEntity> products = productService.getProducts();

        Random random = new Random();
        int productNumber = random.nextInt(products.size() - 1);
        int discountRate = random.nextInt(5, 11);

        productService.setDiscountRate(products.get(productNumber), discountRate);
    }
}
