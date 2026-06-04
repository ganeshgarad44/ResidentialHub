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

import com.example.demo.dto.BedDto;
import com.example.demo.dto.BedGetDto;
import com.example.demo.dto.VacentBedDto;
import com.example.demo.entity.Beds;
import com.example.demo.interfaces.BedServiceInt;

@RestController
public class BedController {

	@Autowired
	BedServiceInt bedServiceInt;

	@PostMapping("bed")
	ResponseEntity<String> addBed(@RequestBody BedDto bedDto) {
		return new ResponseEntity<String>(bedServiceInt.addBed(bedDto), HttpStatus.CREATED);
	}

	@DeleteMapping("bed/{id}")
	ResponseEntity<String> deleteBed(@PathVariable int id) {
		return new ResponseEntity<String>(bedServiceInt.deleteBed(id), HttpStatus.OK);
	}

	@GetMapping("bed/{id}")
	ResponseEntity<Beds> getBed(@PathVariable int id) {
		return new ResponseEntity<Beds>(bedServiceInt.getBed(id), HttpStatus.OK);
	}

	@GetMapping("beds")
	ResponseEntity<List<BedGetDto>> getAllBeds() {
		return new ResponseEntity<List<BedGetDto>>(bedServiceInt.getAllBeds(), HttpStatus.OK);
	}

//	@GetMapping("bedss")
//	ResponseEntity<List<VacentBedDto>> getAllBed(@RequestParam String status, @RequestParam int sharing) {
//		return new ResponseEntity<List<VacentBedDto>>(bedServiceInt.getVacentBed(status, sharing), HttpStatus.OK);
//	}

	@GetMapping("bedss")
	ResponseEntity<List<VacentBedDto>> getAllBed(@RequestParam String status, @RequestParam int sharing,
			@RequestParam(required = false) Integer hostelId) {
		if (hostelId != null) {
			return new ResponseEntity<List<VacentBedDto>>(
					bedServiceInt.getVacantBedsByHostel(status, sharing, hostelId), HttpStatus.OK);
		}
		return new ResponseEntity<List<VacentBedDto>>(bedServiceInt.getVacentBed(status, sharing), HttpStatus.OK);
	}

}
