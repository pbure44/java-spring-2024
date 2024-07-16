package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Secured({"ROLE_SELLER", "ROLE_ADMIN"})
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {

        Product savedProduct = productRepository.save(productMapper.mapToEntity(productDto));

        return productMapper.mapToDto(savedProduct);
    }

    public Optional<ProductDto> findProductById(Long id) {
        return productRepository
                .findById(id)
                .map(productMapper::mapToDto);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto dtoUpdateWith) {
        return productRepository
                .findById(id)
                .map(product -> productMapper.updatePartially(product, dtoUpdateWith))
                .map(productMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Product with id '%s' not found".formatted(id)));
    }

    public void deleteProduct(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        productRepository.findById(id)
                .filter(product -> StringUtils.equalsIgnoreCase(product.getOwner(), authentication.getPrincipal().toString()))
                .orElseThrow(() -> new IllegalStateException("Not your product"));
        productRepository.deleteById(id);
    }

    public List<ProductDto> getProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::mapToDto)
                .toList();
    }

    public boolean existsById(Long productId) {
        return productRepository.existsById(productId);
    }
}
