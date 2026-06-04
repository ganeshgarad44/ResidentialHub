package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.dto.BookingDto;
import com.example.demo.dto.BookingGetDto;
import com.example.demo.entity.Bookings;

public interface BookingServiceInt {

	String addBooking(BookingDto bookingDto);

	Bookings getBooking(int id);

	List<BookingGetDto> getAllBookings();

	List<BookingGetDto> getBookingsByUserId(int userId);
}
