package com.example.demo.service;

import java.time.LocalDate;

public class BookingConfirmedEvent {

	private final String userEmail;
	private final int bedId;
	private final LocalDate bookingDate;

	public BookingConfirmedEvent(String userEmail, int bedId, LocalDate bookingDate) {
		this.userEmail = userEmail;
		this.bedId = bedId;
		this.bookingDate = bookingDate;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public int getBedId() {
		return bedId;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}
}
