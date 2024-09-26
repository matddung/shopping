package com.studyjun.shopping.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "cart")
public class Cart {
    @Id
    private String userId;
    private List<String> productId;

    @Builder
    public Cart(String userId, List<String> productId) {
        this.userId = userId;
        this.productId = productId;
    }
}