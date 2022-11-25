package com.example.giswebservice.services;

import com.example.giswebservice.entities.Product;
import com.example.giswebservice.repositories.ProductRepository;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

@Service
@EnableWebSecurity
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepo = productRepository;
    }


    @Override
    public Product updateProduct(Product product) {
        return productRepo.save(product);
    }
}
