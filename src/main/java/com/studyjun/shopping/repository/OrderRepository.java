package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
