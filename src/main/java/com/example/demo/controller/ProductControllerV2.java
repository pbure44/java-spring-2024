package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.facade.ReviewFacade;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v2")
public class ProductControllerV2 {

    private final ProductService productService;


    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.of(productService.findProductById(id));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    //variant 2
    private final ReviewFacade reviewFacade;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(reviewFacade.getReviews(productId));
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ReviewDto> getReviews(@PathVariable("productId") Long productId, @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewFacade.createReview(productId, reviewDto));
    }
}
