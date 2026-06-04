package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class OrganisationServiceException extends RuntimeException {

	private String messsage;
	private HttpStatus httpStatus;

	public String getMesssage() {
		return messsage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public OrganisationServiceException(String messsage, HttpStatus httpStatus) {
		super();
		this.messsage = messsage;
		this.httpStatus = httpStatus;
	}
}
