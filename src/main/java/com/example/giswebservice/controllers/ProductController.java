package com.example.giswebservice.controllers;

import com.example.giswebservice.entities.Product;
import com.example.giswebservice.repositories.ProductRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private final ProductRepository repository;


    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products/")
    List<Product> getAll() {
        return repository.findAll();
    }

    @GetMapping("/products/category/{category}")
    List<Product> getAllByCategory(@PathVariable String category) {
        return repository.findProductsByCategory(category);
    }

    @GetMapping("/products/search")
    List<Product> findProducts(@RequestParam("keyword") String keyword) {
        return repository.findProductsByNameContainingIgnoreCase(keyword);
    }

    @PostMapping("/products/")
    Product addProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    @GetMapping("/products/{id}")
    Optional<Product> getProduct(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PutMapping("/products/{id}")
    Product replaceEmployee(@RequestBody Product product, @PathVariable Long id) {

        return repository.findById(id)
                .map(old_pr -> {
                    old_pr.setName(product.getName());
                    old_pr.setDescription(product.getDescription());
                    old_pr.setPrice(product.getPrice());
                    old_pr.setImg_url(product.getImg_url());
                    old_pr.setPage_url(product.getPage_url());
                    old_pr.setShop_name(product.getShop_name());
                    old_pr.setCategory(product.getCategory());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    product.setId(id);
                    return repository.save(product);
                });
    }

    @DeleteMapping("/products/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
