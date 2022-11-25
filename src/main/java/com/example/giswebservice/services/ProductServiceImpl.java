package com.example.giswebservice.services;

import com.example.giswebservice.entities.Product;
import com.example.giswebservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableWebSecurity
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepo = productRepository;
    }


    //Обновление, добавление товара
    @Override
    public Product updateProduct(Product product) {
        return productRepo.save(product);
    }

    //Взять все продукты, пагинация
    @Override
    public List<Product> getProducts(Integer page, Integer pageSize) {
        //Создаем объект Pageable для пагинации с страницы page в размере pageSize
        Pageable paging = PageRequest.of(page, pageSize);

        //Находим все товары с пагинацией используя репозиторий товаров
        Page<Product> pagedResult = productRepo.findAll(paging);

        return pagedResult.getContent();
    }

    //Поиск товаров по слову
    @Override
    public List<Product> findProducts(String keyword, Integer page, Integer pageSize) {
        //Создаем объект Pageable для пагинации с страницы page в размере pageSize
        Pageable paging = PageRequest.of(page, pageSize);

        //Находим все товары с именем содержащее слово keyword с пагинацией используя репозиторий товаров
        List<Product> pagedResult = productRepo.findProductsByNameContainingIgnoreCase(keyword, paging);

        return pagedResult;
    }

    //Найти продукты по категории
    @Override
    public List<Product> getProductsByCategory(String category, Integer page, Integer pageSize) {
        //Создаем объект Pageable для пагинации с страницы page в размере pageSize
        Pageable paging = PageRequest.of(page, pageSize);

        //Находим все товары по категории
        List<Product> pagedResult = productRepo.findProductsByCategoryIgnoreCase(category, paging);

        return pagedResult;
    }
}
