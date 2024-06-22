package com.adi685.blogging_web_app.service.impl;

import com.adi685.blogging_web_app.DTO.UserDTO;
import com.adi685.blogging_web_app.entity.User;
import com.adi685.blogging_web_app.repositories.UserRepository;
import com.adi685.blogging_web_app.service.UserService;
import com.adi685.blogging_web_app.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final ModelMapper modelMapper;

	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDTO addUser(UserDTO userDTO) {
		User user = this.dtoToUser(userDTO);
		User savedUser = userRepository.save(user);

		return this.userToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		User updatedUser = userRepository.save(user);

		return this.userToDto(updatedUser);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> userList = userRepository.findAll();

		return userList.stream().map(this::userToDto).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserById(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		return this.userToDto(user);
	}

	@Override
	public void deleteUser(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
		userRepository.delete(user);
	}

	private User dtoToUser(UserDTO userDTO) {
		return modelMapper.map(userDTO, User.class);
		// User user = new User();
		// user.setName(userDTO.getName());
		// user.setEmail(userDTO.getEmail());
		// user.setPassword(userDTO.getPassword());
		// user.setAbout(userDTO.getAbout());
		//
		// return user;
	}

	private UserDTO userToDto(User user) {
		return modelMapper.map(user, UserDTO.class);
		// UserDTO userDTO = new UserDTO();
		// userDTO.setName(user.getName());
		// userDTO.setEmail(user.getEmail());
		// userDTO.setPassword(user.getPassword());
		// userDTO.setAbout(user.getAbout());
		//
		// return userDTO;
	}

}
