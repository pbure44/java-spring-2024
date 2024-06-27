package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewDto {

    private Long productId;

    private String text;

    private Integer rating;

    private LocalDateTime timestamp;
}
