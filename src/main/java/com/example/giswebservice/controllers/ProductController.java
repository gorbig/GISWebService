package com.example.giswebservice.controllers;

import com.example.giswebservice.entities.Product;
import com.example.giswebservice.repositories.ProductRepository;
import com.example.giswebservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private final ProductRepository repository;
    private final ProductService productService;

    public ProductController(ProductRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    //Взять все товары по категории
    @GetMapping("/products/category/{category}")
    List<Product> getAllByCategory(@RequestParam(defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @PathVariable String category) {
        return productService.getProductsByCategory(category, page, pageSize);
    }

    //Поиск продуктов по названию, все товары
    @GetMapping("/products")
    List<Product> findProducts(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        return productService.findProducts(keyword, page, pageSize);
    }

    //Добавление товара
    @PostMapping("/products/")
    Product addProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    //Взять товар по id
    @GetMapping("/products/{id}")
    Optional<Product> getProduct(@PathVariable Long id) {
        return repository.findById(id);
    }

    //Обновить данные товара по id
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

    //Удалить продукт по id
    @DeleteMapping("/products/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
