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

import com.example.demo.dto.BookingDto;
import com.example.demo.dto.BookingGetDto;
import com.example.demo.entity.Bookings;
import com.example.demo.interfaces.BookingServiceInt;

@RestController
public class BookingController {

	@Autowired
	BookingServiceInt bookingServiceInt;

	@PostMapping("booking")
	ResponseEntity<String> addBooking(@RequestBody BookingDto bookingDto) {
		return new ResponseEntity<String>(bookingServiceInt.addBooking(bookingDto), HttpStatus.CREATED);
	}

	@GetMapping("booking/{id}")
	ResponseEntity<Bookings> getBooking(@PathVariable int id) {
		return new ResponseEntity<Bookings>(bookingServiceInt.getBooking(id), HttpStatus.OK);
	}

	@GetMapping("bookings")
	ResponseEntity<List<BookingGetDto>> getAllBookings() {
		return new ResponseEntity<List<BookingGetDto>>(bookingServiceInt.getAllBookings(), HttpStatus.OK);
	}

	@GetMapping("bookings/user/{userId}")
	ResponseEntity<List<BookingGetDto>> getBookingsByUserId(@PathVariable int userId) {
		return new ResponseEntity<List<BookingGetDto>>(bookingServiceInt.getBookingsByUserId(userId), HttpStatus.OK);
	}
}
