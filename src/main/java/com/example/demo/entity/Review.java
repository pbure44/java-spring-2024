package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Builder
@Document("productReviews")
public class Review {

    @MongoId
    private ObjectId id;

    private Long productId;

    private String text;

    private Integer rating;

    private LocalDateTime timestamp;
}
