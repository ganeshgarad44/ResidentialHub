package com.example.demo.controller; // Or change to com.example.demo.service if moving it

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendBookingNotificationToOwner(String ownerEmail, String customerEmail, String messageDetails) {
		SimpleMailMessage s = new SimpleMailMessage();
		s.setTo(ownerEmail);
		s.setSubject("New Room Booking Alert");
		s.setText("A new room has been booked!\n\n" + "Customer Email: " + customerEmail + "\n" + "Details: "
				+ messageDetails);

		javaMailSender.send(s);
	}
}