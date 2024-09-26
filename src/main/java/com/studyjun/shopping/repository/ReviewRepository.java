package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}