package com.example.productapi.service.product;

import com.example.productapi.entity.Product;
import com.example.productapi.model.product.CreateProductRequest;
import com.example.productapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(CreateProductRequest createProductRequest) {
        Product product = new Product(createProductRequest.getId(),
                createProductRequest.getName(),
                createProductRequest.getPrice());

        log.info("saved new product");
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        log.info("get product with id {}", id);
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getProducts() {
        log.info("get all products");
        return productRepository.findAll();
    }
}
