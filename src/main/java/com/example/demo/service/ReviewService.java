package com.example.demo.service;

import com.example.demo.dto.ReviewDto;
import com.example.demo.entity.Review;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewDto saveReview(Long productId, ReviewDto reviewDto) {
        Review review = reviewMapper.mapToEntity(reviewDto);
        review.setProductId(productId);
        review.setTimestamp(LocalDateTime.now());
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.mapToDto(savedReview);
    }

    public List<ReviewDto> getReviews(Long productId) {
        return reviewRepository
                .findAllByProductId(productId)
                .stream()
                .map(reviewMapper::mapToDto)
                .toList();
    }
}
