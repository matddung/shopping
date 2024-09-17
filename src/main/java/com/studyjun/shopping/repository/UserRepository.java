package com.studyjun.shopping.repository;

import com.studyjun.shopping.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}