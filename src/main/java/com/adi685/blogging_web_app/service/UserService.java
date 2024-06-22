package com.adi685.blogging_web_app.service;

import com.adi685.blogging_web_app.DTO.UserDTO;

import java.util.List;

public interface UserService {

	UserDTO addUser(UserDTO user);

	UserDTO updateUser(UserDTO user, Integer id);

	List<UserDTO> getAllUsers();

	UserDTO getUserById(Integer id);

	void deleteUser(Integer id);

}
