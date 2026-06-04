package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Integer> {

}
