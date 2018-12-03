package com.key.tindog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfileNotFoundException extends RuntimeException {

	public ProfileNotFoundException(String message) {
		super(message);
	}

	public ProfileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
