package com.adi685.blogging_web_app.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	private final String resourceName;

	private final String fieldName;

	private int fieldValue;

	private String fieldValue1;

	public ResourceNotFoundException(String resourceName, String fieldName, int fieldValue) {
		super(String.format("%s not found with %s : %d", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue1 = fieldValue;
	}
}
