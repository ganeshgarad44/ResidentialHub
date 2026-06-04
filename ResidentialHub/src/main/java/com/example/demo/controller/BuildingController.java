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

import com.example.demo.dto.BuildingDto;
import com.example.demo.dto.BuildingGetDto;
import com.example.demo.entity.Buildings;
import com.example.demo.interfaces.BuildingServiceInt;

@RestController
public class BuildingController {

	@Autowired
	BuildingServiceInt buildingServiceInt;

	@PostMapping("building")
	ResponseEntity<String> addBuilding(@RequestBody BuildingDto buildingDto) {
		return new ResponseEntity<String>(buildingServiceInt.addBuiding(buildingDto), HttpStatus.CREATED);
	}

	@DeleteMapping("building/{id}")
	ResponseEntity<String> deleteBuilding(@PathVariable int id) {
		return new ResponseEntity<String>(buildingServiceInt.deleteBuilding(id), HttpStatus.OK);
	}

	@GetMapping("building/{id}")
	ResponseEntity<Buildings> getBuilding(@PathVariable int id) {
		return new ResponseEntity<Buildings>(buildingServiceInt.getBuilding(id), HttpStatus.OK);
	}

//	@GetMapping("buildings")
//	ResponseEntity<List<BuildingGetDto>> getAllbuilding() {
//		return new ResponseEntity<List<BuildingGetDto>>(buildingServiceInt.getAllBuilding(), HttpStatus.OK);
//	}

	@GetMapping("buildings")
	ResponseEntity<List<BuildingGetDto>> getAllbuilding(@RequestParam(required = false) Integer hostelId) {
		if (hostelId != null) {
			return new ResponseEntity<List<BuildingGetDto>>(buildingServiceInt.getBuildingsByHostelId(hostelId),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<BuildingGetDto>>(buildingServiceInt.getAllBuilding(), HttpStatus.OK);
	}

}
