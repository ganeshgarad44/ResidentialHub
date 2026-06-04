package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.demo.controller.EmailSender;

@Component
public class BookingConfirmedListener {

	private static final Logger log = LoggerFactory.getLogger(BookingConfirmedListener.class);

	@Autowired
	private EmailSender emailSender;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleBookingConfirmed(BookingConfirmedEvent event) {
		try {
			String email = event.getUserEmail();
			if (email != null && !email.isEmpty()) {
				log.info("Sending booking confirmation email to {} for bed {}", email, event.getBedId());
				emailSender.sendBookingNotificationToOwner(email, email,
						"Booking confirmed for bed " + event.getBedId() + " on " + event.getBookingDate());
				log.info("Booking confirmation email sent successfully to {}", email);
			}
		} catch (Exception e) {
			log.error("Failed to send booking confirmation email to {}: {}", event.getUserEmail(), e.getMessage());
		}
	}
}
