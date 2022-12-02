package com.example.productapi.model.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    private String id;
    private String name;
    private float price;
}
