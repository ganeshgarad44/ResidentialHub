package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PaymentDto;
import com.example.demo.dto.PaymentGetDto;
import com.example.demo.entity.Payments;
import com.example.demo.interfaces.PaymentServiceInt;

@RestController
public class PaymentController {

	@Autowired
	PaymentServiceInt paymentServiceInt;

	@PostMapping("payment")
	ResponseEntity<String> addPayment(@RequestBody PaymentDto paymentDto) {
		return new ResponseEntity<String>(paymentServiceInt.addPayment(paymentDto), HttpStatus.CREATED);
	}

	@GetMapping("payment/{id}")
	ResponseEntity<Payments> getPayment(@PathVariable int id) {
		return new ResponseEntity<Payments>(paymentServiceInt.getPayment(id), HttpStatus.OK);
	}

	ResponseEntity<List<PaymentGetDto>> getAllpayments() {
		return new ResponseEntity<List<PaymentGetDto>>(paymentServiceInt.getAllPayments(), HttpStatus.OK);
	}
}
