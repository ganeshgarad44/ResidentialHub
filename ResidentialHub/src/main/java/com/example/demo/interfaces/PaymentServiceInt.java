package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.dto.PaymentDto;
import com.example.demo.dto.PaymentGetDto;
import com.example.demo.entity.Payments;

public interface PaymentServiceInt {

	String addPayment(PaymentDto paymentDto);

	Payments getPayment(int id);

	List<PaymentGetDto> getAllPayments();
}
