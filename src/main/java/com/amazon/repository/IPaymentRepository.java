package com.amazon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.entity.Payment;

public interface IPaymentRepository extends JpaRepository<Payment, Long> {

}
