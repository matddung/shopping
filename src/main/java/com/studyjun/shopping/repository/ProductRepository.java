package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByStockQuantityLessThanEqual(int stockQuantity);
}
