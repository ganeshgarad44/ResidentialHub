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

import com.example.demo.dto.RoomDto;
import com.example.demo.dto.RoomGetDto;
import com.example.demo.entity.Rooms;
import com.example.demo.interfaces.RoomServiceInt;

@RestController
public class RoomController {

	@Autowired
	RoomServiceInt roomServiceInt;

	@PostMapping("room")
	ResponseEntity<String> addRoom(@RequestBody List<RoomDto> listRoomDto) {
		return new ResponseEntity<String>(roomServiceInt.addRoom(listRoomDto), HttpStatus.CREATED);
	}

	@DeleteMapping("room/{id}")
	ResponseEntity<String> deleteRoom(@PathVariable int id) {
		return new ResponseEntity<String>(roomServiceInt.deleteRoom(id), HttpStatus.OK);
	}

	@GetMapping("room/{id}")
	ResponseEntity<Rooms> getRoom(@PathVariable int id) {
		return new ResponseEntity<Rooms>(roomServiceInt.getRoom(id), HttpStatus.OK);
	}

//	@GetMapping("rooms")
//	ResponseEntity<List<RoomGetDto>> getAllRoom() {
//		return new ResponseEntity<List<RoomGetDto>>(roomServiceInt.getAllRoom(), HttpStatus.OK);
//	}
	
	@GetMapping("rooms")
	ResponseEntity<List<RoomGetDto>> getAllRoom(@RequestParam(required = false) Integer floorId) {
		if (floorId != null) {
			return new ResponseEntity<List<RoomGetDto>>(roomServiceInt.getRoomsByFloorId(floorId), HttpStatus.OK);
		}
		return new ResponseEntity<List<RoomGetDto>>(roomServiceInt.getAllRoom(), HttpStatus.OK);
	}
}