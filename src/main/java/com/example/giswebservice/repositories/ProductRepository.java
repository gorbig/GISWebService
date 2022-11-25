package com.example.giswebservice.repositories;

import com.example.giswebservice.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Найти товары по категории
    List<Product> findProductsByCategoryIgnoreCase(String category, Pageable pageable);
    //Найти товары по имени
    List<Product> findProductsByNameContainingIgnoreCase(String keyword);
    //Найти товары по имени, пагинация
    List<Product> findProductsByNameContainingIgnoreCase(String keyword, Pageable pageable);
}

