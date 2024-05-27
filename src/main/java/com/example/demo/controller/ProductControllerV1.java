package com.example.demo.controller;

import com.example.demo.dao.ProductDao;
import com.example.demo.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("v1")
public class ProductControllerV1 {
    private final ProductDao productDao;

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productDao.getProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(product);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productDao.createProduct(product));
    }
}
