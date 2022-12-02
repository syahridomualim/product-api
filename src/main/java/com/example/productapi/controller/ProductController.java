package com.example.productapi.controller;

import com.example.productapi.entity.Product;
import com.example.productapi.model.HttpResponse;
import com.example.productapi.model.product.CreateProductRequest;
import com.example.productapi.service.product.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity createProduct(@RequestBody CreateProductRequest createProductRequest) {
        Product product = productService.createProduct(createProductRequest);
        return getResponse(product, "Successfully create product");
    }

    @GetMapping("products/{id}")
    public ResponseEntity getProduct(@PathVariable String id) {
        Product product = productService.getProductById(id).orElseThrow(
                () -> {
                    log.error("product by id {} doesn't exists", id);
                    throw new NoSuchElementException(String.format("product by id %s doesn't exists", id));
                }
        );
        return getResponse(product, String.format("Successfully get product by id %s", id));
    }

    @GetMapping(value = "/products")
    public ResponseEntity getAllProducts() {
        List<Product> response = productService.getProducts();
        return getResponse(response, "Successfully get all product");
    }

    private static <T> ResponseEntity<HttpResponse<T>> getResponse(T data, String message) {
        HttpResponse<T> httpResponse = new HttpResponse<T>(
                new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                message,
                data
        );

        return new ResponseEntity(httpResponse, HttpStatus.OK);
    }
}
