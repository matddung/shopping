package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
}
