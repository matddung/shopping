package com.studyjun.shopping.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "payment")
public class Payment {
    @Id
    private String id;
    private String orderId;
    private String userId;
    private int amount;
    private String status; // PAID, FAILED, CANCELLED
    private String method; // CARD, ACCOUNT, PHONE
    private String transactionId;
    private LocalDateTime paymentAt;

    @Builder
    public Payment(String orderId, String userId, int amount, String status, String method, String transactionId, LocalDateTime paymentAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.transactionId = transactionId;
        this.paymentAt = paymentAt;
    }
}