package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {
}
