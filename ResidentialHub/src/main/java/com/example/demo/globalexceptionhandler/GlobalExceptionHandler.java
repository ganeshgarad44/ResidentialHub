package com.example.demo.globalexceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.AddressServiceException;
import com.example.demo.exception.BedServiceException;
import com.example.demo.exception.BookingServiceExeption;
import com.example.demo.exception.BuildingServiceException;
import com.example.demo.exception.FloorServiceException;
import com.example.demo.exception.HostelServiceException;
import com.example.demo.exception.OrganisationServiceException;
import com.example.demo.exception.PaymentServiceException;
import com.example.demo.exception.RoomServiceException;
import com.example.demo.exception.UsersServiceException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(exception = OrganisationServiceException.class)
	ResponseEntity<String> handleOrganisationServiceException(OrganisationServiceException ose) {
		return new ResponseEntity<String>(ose.getMesssage(), ose.getHttpStatus());
	}

	@ExceptionHandler(exception = HostelServiceException.class)
	ResponseEntity<String> handleHostelServiceException(HostelServiceException hse) {
		return new ResponseEntity<String>(hse.getMessage(), hse.getHttpStatus());
	}

	@ExceptionHandler(exception = BuildingServiceException.class)
	ResponseEntity<String> handleBuildingServiceException(BuildingServiceException bse) {
		return new ResponseEntity<String>(bse.getMessage(), bse.getHttpStatus());
	}

	@ExceptionHandler(exception = FloorServiceException.class)
	ResponseEntity<String> handleFloorServiceException(FloorServiceException fse) {
		return new ResponseEntity<String>(fse.getMessage(), fse.getHttpStatus());
	}

	@ExceptionHandler(exception = RoomServiceException.class)
	ResponseEntity<String> handleRoomServiceService(RoomServiceException rse) {
		return new ResponseEntity<String>(rse.getMessage(), rse.getHttpStatus());
	}

	@ExceptionHandler(exception = BedServiceException.class)
	ResponseEntity<String> handleBedServiceException(BedServiceException bse) {
		return new ResponseEntity<String>(bse.getMessage(), bse.getHttpStatus());
	}

	@ExceptionHandler(exception = UsersServiceException.class)
	ResponseEntity<String> handleUsersServiceException(UsersServiceException use) {
		return new ResponseEntity<String>(use.getMessage(), use.getHttpStatus());
	}

	@ExceptionHandler(exception = BookingServiceExeption.class)
	ResponseEntity<String> handleBookingServiceExceptioEntity(BookingServiceExeption bse) {
		return new ResponseEntity<String>(bse.getMessage(), bse.getHttpStatus());
	}

	@ExceptionHandler(exception = PaymentServiceException.class)
	ResponseEntity<String> handlePaymentServiceException(PaymentServiceException pse) {
		return new ResponseEntity<String>(pse.getMessage(), pse.getHttpStatus());
	}

	@ExceptionHandler(exception = AddressServiceException.class)
	ResponseEntity<String> handleAddressServiceException(AddressServiceException ase) {
		return new ResponseEntity<String>(ase.getMessage(), ase.getHttpStatus());
	}

	@ExceptionHandler(exception = Exception.class)
	ResponseEntity<String> handleException() {
		return new ResponseEntity<String>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
