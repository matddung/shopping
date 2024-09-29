package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByStockQuantityLessThanEqual(int stockQuantity);
    List<Product> findByCategory(String category);
    List<Product> findByNameContaining(String name, Sort sort);
    List<Product> findByNameContainingAndCategory(String name, String category, Sort sort);
}