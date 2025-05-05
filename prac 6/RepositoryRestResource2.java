package com.example.repository;

import com.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "products")
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Кастомный запрос 1
    @Query("SELECT p FROM Product p WHERE p.price > :minPrice")
    List<Product> findByPriceGreaterThan(@Param("minPrice") double minPrice);
    
    // Кастомный запрос 2
    List<Product> findByNameContainingIgnoreCase(String name);
}