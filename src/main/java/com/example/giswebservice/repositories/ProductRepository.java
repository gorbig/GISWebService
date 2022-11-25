package com.example.giswebservice.repositories;

import com.example.giswebservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByCategory(String category);
    List<Product> findProductsByNameContainingIgnoreCase(String keyword);
}

