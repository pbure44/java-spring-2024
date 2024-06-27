package com.example.demo.mapper;

import com.example.demo.dto.ReviewDto;
import com.example.demo.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public Review mapToEntity(ReviewDto dto) {
        return Review.builder()
                .text(dto.getText())
                .rating(dto.getRating())
                .build();
    }

    public ReviewDto mapToDto(Review review) {
        return ReviewDto.builder()
                .text(review.getText())
                .rating(review.getRating())
                .productId(review.getProductId())
                .timestamp(review.getTimestamp())
                .build();
    }
}
