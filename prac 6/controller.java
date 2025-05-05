package com.example.controller;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {

    private final ProductRepository repository;

    public ProductViewController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", repository.findAll());
        return "products";
    }
}