package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BookingDto;
import com.example.demo.dto.BookingGetDto;
import com.example.demo.entity.Beds;
import com.example.demo.entity.Bookings;
import com.example.demo.entity.Users;
import com.example.demo.interfaces.BookingServiceInt;
import com.example.demo.repository.BedRepository;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BookingServiceImpl implements BookingServiceInt {

	private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	BedRepository bedRepository;

	@Autowired
	UserRepository usersRepository;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public String addBooking(BookingDto bookingDto) {

		Beds beds = bedRepository.findById(bookingDto.getBedId()).get();
		if (beds.getStatus().equalsIgnoreCase("Occupied") || beds.getStatus().equalsIgnoreCase("Booked")) {
			return "Bed already Booked";
		}
		beds.setStatus("Booked");
		bedRepository.save(beds);

		Optional<Users> o = usersRepository.findById(bookingDto.getUserId());
		if (o.isEmpty()) {
			return "No user regarding : " + bookingDto.getUserId();
		}
		Users users = o.get();

		Bookings bookings = new Bookings();
		bookings.setStatus("PENDING");
		bookings.setPaymentStatus("UNPAID");
		bookings.setDate(LocalDate.now());
		bookings.setBeds(beds);
		bookings.setUsers(users);

		bookingRepository.save(bookings);

		eventPublisher.publishEvent(
				new BookingConfirmedEvent(users.getEmail(), bookingDto.getBedId(), bookings.getDate()));

		return "Booking Saved";
	}

	@Override
	public Bookings getBooking(int id) {
		return bookingRepository.findById(id).get();
	}

	@Override
	public List<BookingGetDto> getAllBookings() {

		List<BookingGetDto> listbgd = new ArrayList<>();

		List<Bookings> lb = bookingRepository.findAll();

		for (Bookings bookings : lb) {

			BookingGetDto dto = new BookingGetDto();
			dto.setBookingId(bookings.getBookingId());
			dto.setStatus(bookings.getStatus());
			dto.setPaymentStatus(bookings.getPaymentStatus());
			dto.setDate(bookings.getDate());
			dto.setBedId(bookings.getBeds().getId());
			dto.setUserId(bookings.getUsers().getUserId());
			listbgd.add(dto);
		}
		return listbgd;
	}

	@Override
	public List<BookingGetDto> getBookingsByUserId(int userId) {

		List<BookingGetDto> listbgd = new ArrayList<>();

		List<Bookings> lb = bookingRepository.findByUsersUserId(userId);

		for (Bookings bookings : lb) {

			BookingGetDto dto = new BookingGetDto();
			dto.setBookingId(bookings.getBookingId());
			dto.setStatus(bookings.getStatus());
			dto.setPaymentStatus(bookings.getPaymentStatus());
			dto.setDate(bookings.getDate());
			dto.setBedId(bookings.getBeds().getId());
			dto.setUserId(bookings.getUsers().getUserId());
			listbgd.add(dto);
		}
		return listbgd;
	}
}
