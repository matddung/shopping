package com.studyjun.shopping.repository;

import com.studyjun.shopping.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {
}
