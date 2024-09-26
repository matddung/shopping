package com.studyjun.shopping.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "review")
public class Review {
    @Id
    private String id;
    private String userId;
    private String productId;
    private int rating;
    private String reviewText;
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Review(String userId, String productId, int rating, String reviewText, LocalDateTime createdAt) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdAt = createdAt;
    }
}