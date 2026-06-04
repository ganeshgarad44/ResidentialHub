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

import com.example.demo.dto.FloorGetDto;
import com.example.demo.dto.FloorsDto;
import com.example.demo.entity.Floors;
import com.example.demo.interfaces.FloorServiceInt;

@RestController
public class FloorController {

	@Autowired
	FloorServiceInt floorServiceInt;

	@PostMapping("floor")
	ResponseEntity<String> addFloors(@RequestBody FloorsDto floorsDto) {
		return new ResponseEntity<String>(floorServiceInt.addFloor(floorsDto), HttpStatus.CREATED);
	}

	@DeleteMapping("floors/{id}")
	ResponseEntity<String> deleteFloor(@PathVariable int id) {
		return new ResponseEntity<String>(floorServiceInt.deleteFloor(id), HttpStatus.OK);
	}

	@GetMapping("floor/{id}")
	ResponseEntity<Floors> getFloor(@PathVariable int id) {
		return new ResponseEntity<Floors>(floorServiceInt.getFloor(id), HttpStatus.OK);
	}

//	@GetMapping("floors")
//	ResponseEntity<List<FloorGetDto>> getAllFloors() {
//		return new ResponseEntity<List<FloorGetDto>>(floorServiceInt.getAllFloor(), HttpStatus.OK);
//	}

	@GetMapping("floors")
	ResponseEntity<List<FloorGetDto>> getAllFloors(@RequestParam(required = false) Integer buildingId) {
		if (buildingId != null) {
			return new ResponseEntity<List<FloorGetDto>>(floorServiceInt.getFloorsByBuildingId(buildingId),
					HttpStatus.OK);
		}
		return new ResponseEntity<List<FloorGetDto>>(floorServiceInt.getAllFloor(), HttpStatus.OK);
	}
}
