package com.adi685.blogging_web_app.exception;

import com.adi685.blogging_web_app.DTO.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
		String errorMessage = ex.getMessage();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		apiResponse.setMessage(errorMessage);
		apiResponse.setStatus("FAILURE");

		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		Map<String, String> result = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			result.put(fieldName, errorMessage);
		});

		return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiResponseDTO> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException ex) {
		String errorMessage = ex.getLocalizedMessage();
		ApiResponseDTO apiResponse = new ApiResponseDTO();
		apiResponse.setMessage(errorMessage);
		apiResponse.setStatus("FAILURE");

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

}
