package com.example.demo.repository;

import com.example.demo.model.PaymentMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends CrudRepository<PaymentMethod,String> {

    Iterable<PaymentMethod> findByEnabledTrue();

}
