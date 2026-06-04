package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HostelGetDto;
import com.example.demo.interfaces.AddressServiceInt;

@RestController
public class AddressController {

	@Autowired
	AddressServiceInt addresserviceInt;

	@GetMapping("address")
	ResponseEntity<List<HostelGetDto>> gethostels(@RequestParam(required = false) String city,
			@RequestParam(required = false) String area) {
		return new ResponseEntity<List<HostelGetDto>>(addresserviceInt.searchHostels(city, area), HttpStatus.OK);
	}

}
