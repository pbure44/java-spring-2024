package com.example.demo.controller;

import com.example.demo.dto.ReviewDto;
import com.example.demo.facade.ReviewFacade;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewFacade reviewFacade;

    //variant 1
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDto>> getReviews(@RequestParam("productId") Long productId) {
        return ResponseEntity.ok(reviewFacade.getReviews(productId));
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewDto> getReviews(@RequestParam("productId") Long productId, @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewFacade.createReview(productId, reviewDto));
    }
}
