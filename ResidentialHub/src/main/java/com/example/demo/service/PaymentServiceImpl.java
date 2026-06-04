package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.demo.dto.PaymentDto;
import com.example.demo.dto.PaymentGetDto;
import com.example.demo.entity.Bookings;
import com.example.demo.entity.Payments;
import com.example.demo.exception.BookingServiceExeption;
import com.example.demo.exception.PaymentServiceException;
import com.example.demo.interfaces.PaymentServiceInt;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentServiceInt {

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	BookingRepository bookingRepository;

	@Override
	public String addPayment(PaymentDto paymentDto) {

		Optional<Bookings> o = bookingRepository.findById(paymentDto.getBookingId());
		if (o.isEmpty()) {
			return "No data regarding : " + paymentDto.getBookingId();
		}
		Bookings bookings = o.get();

		if (!bookings.getPaymentStatus().equalsIgnoreCase("UNPAID")) {
			return "Already Paid";
		}

		Payments pay = new Payments();
		pay.setTransactionId(paymentDto.getTransactionId());
		pay.setAmount(paymentDto.getAmount());
		pay.setStatus(paymentDto.getStatus());
		pay.setMethod(paymentDto.getMethod());
		pay.setTime(paymentDto.getTime());
		pay.setBookings(bookings);

		bookings.setStatus("CONFIRMED");
		bookings.setPaymentStatus("PAID");

		if (bookings.getBeds() != null) {
			bookings.getBeds().setStatus("Occupied");
		}
		bookingRepository.save(bookings);

		try {
			paymentRepository.save(pay);
		} catch (Exception e) {
			throw new PaymentServiceException("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return "Payment Sucessful";
	}

	@Override
	public Payments getPayment(int id) {
		if (id <= 0) {
			throw new BookingServiceExeption("Invalid Id", HttpStatus.BAD_REQUEST);
		}
		Optional<Payments> o = paymentRepository.findById(id);
		if (o.isEmpty()) {
			throw new BookingServiceExeption("Not found", HttpStatus.NOT_FOUND);
		}
		return o.get();
	}

	@Override
	public List<PaymentGetDto> getAllPayments() {
		List<PaymentGetDto> listpgd = new ArrayList<>();

		List<Payments> lo = paymentRepository.findAll();

		for (Payments payments : lo) {

			PaymentGetDto dto = new PaymentGetDto();
			dto.setId(payments.getId());
			dto.setTransactionId(payments.getTransactionId());
			dto.setAmount(payments.getAmount());
			dto.setStatus(payments.getStatus());
			dto.setMethod(payments.getMethod());
			dto.setTime(payments.getTime());
			dto.setBookingId(payments.getBookings().getBookingId());
			listpgd.add(dto);
		}
		return listpgd;
	}
}
