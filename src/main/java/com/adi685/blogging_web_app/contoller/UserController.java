package com.adi685.blogging_web_app.contoller;

import com.adi685.blogging_web_app.DTO.UserDTO;
import com.adi685.blogging_web_app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adi685.blogging_web_app.enums.Constants.DELETED_SUCCESFULLY_STRING;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/")
	private ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
		UserDTO createdUserDTO = userService.addUser(userDTO);

		return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	}

	@GetMapping("/")
	private ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> userDTOList = userService.getAllUsers();

		return new ResponseEntity<>(userDTOList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	private ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
		UserDTO userDTO = userService.getUserById(id);

		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	private ResponseEntity<UserDTO> getUserById(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO) {
		UserDTO updatedUserDTO = userService.updateUser(userDTO, id);

		return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<String> deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(DELETED_SUCCESFULLY_STRING, HttpStatus.OK);
	}

}
