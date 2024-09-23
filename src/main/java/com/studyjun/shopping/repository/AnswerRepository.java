package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {
}
