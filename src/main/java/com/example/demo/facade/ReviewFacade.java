package com.example.demo.facade;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.dto.SendMailDto;
import com.example.demo.service.MailService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewFacade {

    private final ProductService productService;

    private final ReviewService reviewService;

    private final MailService mailService;

    //    Variant 1 to =create review=
    public ReviewDto createReview(Long productId, ReviewDto reviewDto) {
        Optional<ProductDto> productOpt = productService.findProductById(productId);
        if (productOpt.isEmpty()) {
            throw new NoSuchElementException("Product with id %s not found".formatted(productId));
        }

        ReviewDto savedReview = reviewService.saveReview(productId, reviewDto);
        String subject = "New review for product %s".formatted(productOpt.get().getName());
        String text = "You have new review with rating %s and text: %s".formatted(savedReview.getRating(), savedReview.getText());

        mailService.sendMail(SendMailDto.builder()
                .subject(subject)
                .text(text)
                .recipient(productOpt.get().getOwner())
                .build());
        return savedReview;
    }

    public List<ReviewDto> getReviews(Long productId) {
        return reviewService.getReviews(productId);
    }

//    Variant 2 to =create review=
//    public ReviewDto createReviewWithChain(Long productId, ReviewDto reviewDto) {
//        return productService
//                .findProductById(productId)
//                .map(product -> {
//                    ReviewDto savedReview = reviewService.saveReview(productId, reviewDto);
//                    String subject = "New review for product %s".formatted(productId);
//                    String text = "You have new review with rating %s and text: '$s'".formatted(savedReview.getRating(), savedReview.getText());
//                    mailService.sendMail(product.getOwner(), subject, text);
//                    return savedReview;
//                })
//                .orElseThrow(() -> new NoSuchElementException("Product with id %s not found".formatted(productId)));
//    }
}
