package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {
    Optional<Token> findByRefreshToken(String refreshToken);
}