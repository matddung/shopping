package com.studyjun.shopping.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private String category;
    private String imageUrl;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Builder
    public Product(String name, String description, int price, int stockQuantity, String category, String imageUrl, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}