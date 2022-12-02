package com.example.productapi.service;

import com.example.productapi.entity.Product;
import com.example.productapi.repository.ProductRepository;
import com.example.productapi.service.product.ProductService;
import com.example.productapi.service.product.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @TestConfiguration
    static class ProductServiceImplTestConfig {

        @Autowired
        private ProductRepository productRepository;

        @Bean
        public ProductService productService() {
            return new ProductServiceImpl(productRepository);
        }
    }

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private List<Product> products = Arrays.asList(
            new Product("A01", "Baju A", 80000),
            new Product("A02", "Baju B", 50000),
            new Product("A03", "Baju C", 60000),
            new Product("A04", "Baju D", 70000)
    );

    @Before
    public void setUp() {
        Product productA = products.get(0);
        Product productB = products.get(1);
        Product productC = products.get(2);
        Product productD = products.get(3);

        productRepository.saveAll(products);

        Mockito.when(productRepository.findById(productA.getId())).thenReturn(Optional.of(productA));
        Mockito.when(productRepository.findById(productB.getId())).thenReturn(Optional.of(productB));
        Mockito.when(productRepository.findById(productC.getId())).thenReturn(Optional.of(productC));
        Mockito.when(productRepository.findById(productD.getId())).thenReturn(Optional.of(productA));
        Mockito.when(productRepository.findAll()).thenReturn(products);
    }

    @Test
    public void whenIdValidThenShouldBeFoundTest() {
        String id = "A01";
        Product product = productService.getProductById(id).orElseThrow(() -> new NoSuchElementException(":("));

        Assertions.assertEquals(product.getId(), id);
        verifyFindByIdIsCalledOnce(id);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenIdInvalidThenShouldBeException() {
        String id = "wrong_id";
        Product product = productService.getProductById(id).orElseThrow(() -> new NoSuchElementException(":("));

        Assertions.assertEquals(product.getId(), id);
        verifyFindByIdIsCalledOnce(id);
    }

    private void verifyFindByIdIsCalledOnce(String id) {
        Mockito.verify(productRepository, VerificationModeFactory.times(1)).findById(id);
        Mockito.reset(productRepository);
    }
}
