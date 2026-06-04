package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UsersDto;
import com.example.demo.dto.UsersGetDto;
import com.example.demo.entity.Users;
import com.example.demo.interfaces.UserServiceInt;

@RestController
public class UsersController {

	@Autowired
	UserServiceInt userServiceInt;

	@PostMapping("user")
	ResponseEntity<UsersGetDto> addUser(@RequestBody UsersDto usersDto) {
		return new ResponseEntity<UsersGetDto>(userServiceInt.saveUsers(usersDto), HttpStatus.CREATED);
	}

	@DeleteMapping("user/{id}")
	ResponseEntity<String> deletUser(@PathVariable int id) {
		return new ResponseEntity<String>(userServiceInt.deleteUsers(id), HttpStatus.OK);
	}

	@GetMapping("user/{id}")
	ResponseEntity<Users> getUser(@PathVariable int id) {
		return new ResponseEntity<Users>(userServiceInt.getUsers(id), HttpStatus.OK);
	}

	@GetMapping("users")
	ResponseEntity<List<UsersGetDto>> getAllUsers() {
		return new ResponseEntity<List<UsersGetDto>>(userServiceInt.getAllUsers(), HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<UsersGetDto> signupUser(@RequestBody UsersDto usersDto) {
		UsersGetDto created = userServiceInt.saveUsers(usersDto);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// Login interface utilizing if/else plain text checking mapping
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
		String email = credentials.get("email");
		String password = credentials.get("password");

		UsersGetDto userProfile = userServiceInt.loginUser(email, password);
		return new ResponseEntity<>(userProfile, HttpStatus.OK);
	}

	// Used by the user profile section inside dashboard panel to modify details
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateDashboardProfile(@PathVariable int id, @RequestBody UsersDto usersDto) {
		String response = userServiceInt.updateUserDetails(id, usersDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
