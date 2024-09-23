package com.studyjun.shopping.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private String category;
    private String imageUrl;
}