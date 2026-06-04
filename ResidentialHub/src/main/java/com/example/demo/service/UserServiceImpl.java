package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UsersDto;
import com.example.demo.dto.UsersGetDto;
import com.example.demo.entity.Users;
import com.example.demo.exception.UsersServiceException;
import com.example.demo.interfaces.UserServiceInt;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServiceInt {

	@Autowired
	UserRepository userRepository;

	@Override
	public UsersGetDto saveUsers(UsersDto usersDto) {

		if (usersDto.getName() == null || usersDto.getName().trim().isEmpty() || usersDto.getCurrentAddress() == null
				|| usersDto.getCurrentAddress().trim().isEmpty() || usersDto.getPermanantAddress() == null
				|| usersDto.getPermanantAddress().trim().isEmpty() || usersDto.getDateOfBirth() == null
				|| usersDto.getEmail() == null || usersDto.getEmail().trim().isEmpty()
				|| usersDto.getEducation() == null || usersDto.getEducation().trim().isEmpty()
				|| usersDto.getParentName() == null || usersDto.getParentName().trim().isEmpty()
				|| usersDto.getGender() == null || usersDto.getGender().trim().isEmpty()
				|| usersDto.getProfession() == null || usersDto.getProfession().trim().isEmpty()
				|| usersDto.isMarried() == null) {
			throw new UsersServiceException("Empty Field", HttpStatus.BAD_REQUEST);
		}

		if (String.valueOf(usersDto.getMobileNo()).length() != 10
				|| String.valueOf(usersDto.getParentMobileNo()).length() != 10) {
			throw new UsersServiceException("Invalid mobile no", HttpStatus.BAD_REQUEST);
		}

		if (String.valueOf(usersDto.getAdharNo()).length() != 12) {
			throw new UsersServiceException("Invalid adhar id", HttpStatus.BAD_REQUEST);
		}

		Users users = new Users();
		users.setName(usersDto.getName());
		users.setCurrentAddress(usersDto.getCurrentAddress());
		users.setPermanantAddress(usersDto.getPermanantAddress());
		users.setDateOfBirth(usersDto.getDateOfBirth());
		users.setEmail(usersDto.getEmail());
		users.setMobileNo(usersDto.getMobileNo());
		users.setEducation(usersDto.getEducation());
		users.setAdharNo(usersDto.getAdharNo());
		users.setParentName(usersDto.getParentName());
		users.setParentMobileNo(usersDto.getParentMobileNo());
		users.setGender(usersDto.getGender());
		users.setProfession(usersDto.getProfession());
		users.setMarried(usersDto.isMarried());
		users.setDate(LocalDate.now());

		try {
			userRepository.save(users);
		} catch (Exception e) {
			throw new UsersServiceException("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		UsersGetDto dto = new UsersGetDto();
		dto.setUserId(users.getUserId());
		dto.setName(users.getName());
		dto.setCurrentAddress(users.getCurrentAddress());
		dto.setPermanantAddress(users.getPermanantAddress());
		dto.setDateOfBirth(users.getDateOfBirth());
		dto.setEmail(users.getEmail());
		dto.setMobileNo(users.getMobileNo());
		dto.setEducation(users.getEducation());
		dto.setAdharNo(users.getAdharNo());
		dto.setParentName(users.getParentName());
		dto.setParentMobileNo(users.getParentMobileNo());
		dto.setGender(users.getGender());
		dto.setProfession(users.getProfession());
		dto.setMarried(users.getMarried());
		dto.setDate(users.getDate());
		return dto;
	}

	@Override
	public String deleteUsers(int id) {
		if (id <= 0) {
			throw new UsersServiceException("Invalid Id", HttpStatus.BAD_REQUEST);
		}
		if (!userRepository.existsById(id)) {
			throw new UsersServiceException("Not found", HttpStatus.NOT_FOUND);
		}
		userRepository.deleteById(id);
		return "User Deleted";
	}

	@Override
	public Users getUsers(int id) {
		if (id <= 0) {
			throw new UsersServiceException("Invalid Id", HttpStatus.BAD_REQUEST);
		}
		Optional<Users> o = userRepository.findById(id);
		if (o.isEmpty()) {
			throw new UsersServiceException("Not Found", HttpStatus.NOT_FOUND);
		}
		return o.get();
	}

	@Override
	public List<UsersGetDto> getAllUsers() {

		List<UsersGetDto> listugd = new ArrayList<>();

		List<Users> ul = userRepository.findAll();

		for (Users users : ul) {

			UsersGetDto dto = new UsersGetDto();
			dto.setUserId(users.getUserId());
			dto.setName(users.getName());
			dto.setCurrentAddress(users.getCurrentAddress());
			dto.setPermanantAddress(users.getPermanantAddress());
			dto.setDateOfBirth(users.getDateOfBirth());
			dto.setEmail(users.getEmail());
			dto.setMobileNo(users.getMobileNo());
			dto.setEducation(users.getEducation());
			dto.setAdharNo(users.getAdharNo());
			dto.setParentName(users.getParentName());
			dto.setParentMobileNo(users.getParentMobileNo());
			dto.setGender(users.getGender());
			dto.setProfession(users.getProfession());
			dto.setMarried(users.isMarried());
			dto.setDate(LocalDate.now());

			listugd.add(dto);
		}
		return listugd;
	}

	@Override
	public UsersGetDto loginUser(String email, String password) {
		if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			throw new UsersServiceException("Email and password fields cannot be empty", HttpStatus.BAD_REQUEST);
		}

		// Look for user record inside database via custom email lookup hook
		com.example.demo.entity.Users matchedUser = userRepository.findByEmail(email.trim()).orElseThrow(
				() -> new UsersServiceException("No account registered with this email address", HttpStatus.NOT_FOUND));

		// IF-ELSE Security validation logic: Compares password inputs safely
		// Note: If you don't have a dedicated password column, we fall back to
		// validating against mobile number string
		String validPassword = String.valueOf(matchedUser.getMobileNo());

		if (!password.trim().equals(validPassword)) {
			throw new UsersServiceException("Invalid email or password credentials entered", HttpStatus.UNAUTHORIZED);
		}

		// Mapping properties to the returning DTO profile packet
		UsersGetDto dto = new UsersGetDto();
		dto.setUserId(matchedUser.getUserId());
		dto.setName(matchedUser.getName());
		dto.setCurrentAddress(matchedUser.getCurrentAddress());
		dto.setPermanantAddress(matchedUser.getPermanantAddress());
		dto.setDateOfBirth(matchedUser.getDateOfBirth());
		dto.setEmail(matchedUser.getEmail());
		dto.setMobileNo(matchedUser.getMobileNo());
		dto.setEducation(matchedUser.getEducation());
		dto.setAdharNo(matchedUser.getAdharNo());
		dto.setParentName(matchedUser.getParentName());
		dto.setParentMobileNo(matchedUser.getParentMobileNo());
		dto.setGender(matchedUser.getGender());
		dto.setProfession(matchedUser.getProfession());
		dto.setMarried(matchedUser.getMarried());
		dto.setDate(matchedUser.getDate());

		return dto;
	}

	@Override
	public String updateUserDetails(int id, UsersDto usersDto) {
		com.example.demo.entity.Users user = userRepository.findById(id).orElseThrow(
				() -> new UsersServiceException("User profile update target not found", HttpStatus.NOT_FOUND));

		// Re-assigning fields cleanly for dashboard saving requests
		user.setName(usersDto.getName());
		user.setCurrentAddress(usersDto.getCurrentAddress());
		user.setPermanantAddress(usersDto.getPermanantAddress());
		user.setDateOfBirth(usersDto.getDateOfBirth());
		user.setEmail(usersDto.getEmail());
		user.setMobileNo(usersDto.getMobileNo());
		user.setEducation(usersDto.getEducation());
		user.setParentName(usersDto.getParentName());
		user.setParentMobileNo(usersDto.getParentMobileNo());
		user.setGender(usersDto.getGender());
		user.setProfession(usersDto.getProfession());
		user.setMarried(usersDto.getMarried());

		userRepository.save(user);
		return "Profile updated successfully";
	}

}
