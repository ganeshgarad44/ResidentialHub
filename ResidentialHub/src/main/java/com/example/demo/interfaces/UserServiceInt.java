package com.example.demo.interfaces;

import java.util.List;
import com.example.demo.dto.UsersDto;
import com.example.demo.dto.UsersGetDto;
import com.example.demo.entity.Users;

public interface UserServiceInt {

	UsersGetDto saveUsers(UsersDto usersDto);

	String deleteUsers(int id);

	Users getUsers(int id);

	List<UsersGetDto> getAllUsers();

	UsersGetDto loginUser(String email, String password);

	String updateUserDetails(int id, UsersDto usersDto);
}