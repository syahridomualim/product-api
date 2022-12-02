package com.example.productapi.service.product;

import com.example.productapi.entity.Product;
import com.example.productapi.model.product.CreateProductRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(CreateProductRequest createProductRequest);

    Optional<Product> getProductById(String id);

    List<Product> getProducts();
}
