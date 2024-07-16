package com.example.demo.job;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.dto.SendMailDto;
import com.example.demo.service.MailService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class SendProductStats {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final MailService mailService;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void sendProductStats() {
        Map<String, List<Pair<ProductDto, Double>>> ownerWithAverageRatings = productService
                .getProducts()
                .stream()
                .flatMap(product -> reviewService
                        .getReviews(product.getId())
                        .stream()
                        .filter(review -> review.getTimestamp() != null)
//                        .filter(review -> review.getTimestamp().isAfter(LocalDateTime.now().minusMinutes(1)))
                        .mapToInt(ReviewDto::getRating)
                        .average()
                        .stream()
                        .mapToObj(averageRating -> Pair.of(product, averageRating)))
                .collect(Collectors.groupingBy(pair -> pair.getKey().getOwner()));

        ownerWithAverageRatings.forEach((owner, ratings) -> {
            double totalAverageRating = ratings
                    .stream()
                    .mapToDouble(Pair::getValue)
                    .average()
                    .orElse(0);

            mailService.sendMail(SendMailDto.builder()
                    .subject("Products total rating for last 1 minute")
                    .text("Your average rating is %s".formatted(totalAverageRating))
                    .recipient(owner)
                    .build());
        });
    }

    ;
}


