package com.example.giswebservice.services;

import com.example.giswebservice.entities.Product;

import java.util.List;

public interface ProductService {
    //Обновление, добавление товара
    Product updateProduct(Product product);
    //Взять все продукты, пагинация
    List<Product> getProducts(Integer page, Integer pageSize);
    //Поиск товаров по слову
    List<Product> findProducts(String keyword, Integer page, Integer pageSize);
    //Найти продукты по категории
    List<Product> getProductsByCategory(String category, Integer page, Integer pageSize);
}
