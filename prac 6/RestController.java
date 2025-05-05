package com.example.controller;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/custom/products")
public class ProductCustomController {

    private final ProductRepository repository;

    public ProductCustomController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/expensive/{minPrice}")
    public CollectionModel<EntityModel<Product>> getExpensiveProducts(@PathVariable double minPrice) {
        List<EntityModel<Product>> products = repository.findByPriceGreaterThan(minPrice).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductCustomController.class).getExpensiveProducts(minPrice)).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(products,
                linkTo(methodOn(ProductCustomController.class).getExpensiveProducts(minPrice)).withSelfRel());
    }

    @GetMapping("/search")
    public CollectionModel<EntityModel<Product>> searchProducts(@RequestParam String name) {
        List<EntityModel<Product>> products = repository.findByNameContainingIgnoreCase(name).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductCustomController.class).searchProducts(name)).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(products,
                linkTo(methodOn(ProductCustomController.class).searchProducts(name)).withSelfRel());
    }
}