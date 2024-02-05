package com.example.demo.repository;

import com.example.demo.model.PaymentMethod;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends CrudRepository<PaymentMethod,String> {

}
