package com.adi685.blogging_web_app.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	private int id;

	@NotBlank(message = "Name should not be blank !!")
	@Size(min = 3, message = "Name should at least contain 3 characters !!")
	private String name;

	@Email(message = "email is invalid !!")
	private String email;

	@NotBlank(message = "Password should not be blank!!")
	@Size(min = 6, message = "Password should contain at least 4 characters !!")
	@Size(max = 12, message = "Password cannot be greater than 12 characters !!")
	private String password;

	private String about;

}
