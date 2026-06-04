package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.demo.entity.Bookings;

public interface BookingRepository extends JpaRepository<Bookings, Integer> {

	List<Bookings> findByUsersUserId(int userId);
}
