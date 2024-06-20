package com.example.demo.dto;

import com.example.demo.entity.ProductAvailability;
import com.example.demo.validation.ValidCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    @Max(value = 1000, message = "must be less than 1000")
    @Min(value = 2, message = "must be more than 2")
    private Double price;

    private ProductAvailability availability;

    @ValidCategory
    private String category;
}
