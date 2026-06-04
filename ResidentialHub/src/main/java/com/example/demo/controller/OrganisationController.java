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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrganisationDto;
import com.example.demo.dto.OrganisationGetDto;
import com.example.demo.entity.Organisation;
import com.example.demo.interfaces.OrganisationServiceInt;

@RestController
public class OrganisationController {

	@Autowired
	OrganisationServiceInt organisationServiceInt;

	@PostMapping("organisation")
	ResponseEntity<String> addOrganisation(@RequestBody OrganisationDto organisationDto) {
		return new ResponseEntity<String>(organisationServiceInt.addOrganisation(organisationDto), HttpStatus.CREATED);
	}

	@DeleteMapping("organisation/{id}")
	ResponseEntity<String> deleteOrganisation(@PathVariable int id) {
		return new ResponseEntity<String>(organisationServiceInt.deletOrganisation(id), HttpStatus.OK);
	}

	@GetMapping("organisation/{id}")
	ResponseEntity<Organisation> getOrganisation(@PathVariable int id) {
		return new ResponseEntity<Organisation>(organisationServiceInt.getOrganisation(id), HttpStatus.OK);
	}

	@GetMapping("organisations")
	ResponseEntity<List<OrganisationGetDto>> getAllOrganisation() {
		return new ResponseEntity<List<OrganisationGetDto>>(organisationServiceInt.getAllOrganisation(), HttpStatus.OK);
	}
}
