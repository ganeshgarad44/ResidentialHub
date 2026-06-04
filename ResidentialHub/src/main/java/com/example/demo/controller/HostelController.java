package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HostelDto;
import com.example.demo.dto.HostelGetDto;
import com.example.demo.entity.Hostels;
import com.example.demo.interfaces.HostelServiceInt;

@RestController
public class HostelController {

	@Autowired
	HostelServiceInt hostelServiceInt;

	@PostMapping("hostel")
	ResponseEntity<String> addHostel(@RequestBody HostelDto hostelDto) {
		return new ResponseEntity<String>(hostelServiceInt.addHostel(hostelDto), HttpStatus.CREATED);
	}

	@DeleteMapping("hostel/{id}")
	ResponseEntity<String> deleteHostel(@PathVariable int id) {
		return new ResponseEntity<String>(hostelServiceInt.deletehostel(id), HttpStatus.OK);
	}

	@GetMapping("hostel/{id}")
	ResponseEntity<Hostels> getHostel(@PathVariable int id) {
		return new ResponseEntity<Hostels>(hostelServiceInt.getHostel(id), HttpStatus.OK);
	}

//	@GetMapping("hostels")
//	ResponseEntity<List<HostelGetDto>> getAllHostels() {
//		return new ResponseEntity<List<HostelGetDto>>(hostelServiceInt.getAllHostel(), HttpStatus.OK);
//	}

	@GetMapping("hostels")
	ResponseEntity<List<HostelGetDto>> getAllHostels(@RequestParam(required = false) Integer organisationId) {
		if (organisationId != null) {
			// Fast filter for landing page hierarchy browse
			return new ResponseEntity<List<HostelGetDto>>(hostelServiceInt.getHostelsByOrganisationId(organisationId), HttpStatus.OK);
		}
		return new ResponseEntity<List<HostelGetDto>>(hostelServiceInt.getAllHostel(), HttpStatus.OK);
	}
}
