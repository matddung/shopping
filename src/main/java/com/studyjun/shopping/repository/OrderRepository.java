package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findByUserIdAndProductIds(String userId, String productId);
}