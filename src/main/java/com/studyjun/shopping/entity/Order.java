package com.studyjun.shopping.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "order")
public class Order {
    @Id
    private String id;
    private String userId;
    private List<String> productIds;
    private int totalPrice;
    private String address;
    private String status; // PENDING, COMPLETED, CANCELLED
    private LocalDateTime orderedAt;

    @Builder
    public Order(String userId, List<String> productIds, int totalPrice, String address, String status, LocalDateTime orderedAt) {
        this.userId = userId;
        this.productIds = productIds;
        this.totalPrice = totalPrice;
        this.address = address;
        this.status = status;
        this.orderedAt = orderedAt;
    }
}