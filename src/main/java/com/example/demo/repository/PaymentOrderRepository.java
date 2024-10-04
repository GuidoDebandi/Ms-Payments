package com.example.demo.repository;

import com.example.demo.model.PaymentOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepository extends MongoRepository<PaymentOrder,String> {

    Iterable<PaymentOrder> findByStatusContainingIgnoreCase(String status);
}
