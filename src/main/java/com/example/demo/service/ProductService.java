package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

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
        productRepository.deleteById(id);
    }

    public List<ProductDto> getProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::mapToDto)
                .toList();
    }
}
