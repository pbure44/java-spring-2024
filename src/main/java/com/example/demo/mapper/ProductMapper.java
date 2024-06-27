package com.example.demo.mapper;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductRepository productRepository;

    public ProductDto mapToDto(Product entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .availability(entity.getAvailability())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .owner(entity.getOwner())
                .build();
    }

    public Product mapToEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setAvailability(dto.getAvailability());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setOwner(dto.getOwner());
        return product;
    }

    public Product updatePartially(Product target, ProductDto updateWith) {
        if (updateWith.getName() != null) {
            target.setName(updateWith.getName());
        }
        if (updateWith.getDescription() != null) {
            target.setDescription(updateWith.getDescription());
        }
        if (updateWith.getPrice() != null) {
            target.setPrice(updateWith.getPrice());
        }
        if (updateWith.getAvailability() != null) {
            target.setAvailability(updateWith.getAvailability());
        }
        if (updateWith.getCategory() != null) {
            target.setCategory(updateWith.getCategory());
        }
        if (updateWith.getOwner() != null) {
            target.setOwner(updateWith.getOwner());
        }
        return target;
    }
}
