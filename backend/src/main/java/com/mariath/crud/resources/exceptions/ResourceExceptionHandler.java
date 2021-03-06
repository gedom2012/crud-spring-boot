package com.mariath.crud.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mariath.crud.services.exceptions.DataBaseException;
import com.mariath.crud.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status)
				.body(setError(status, e.getMessage(), request, "Resource not found."));
	}

	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> dataBase(DataBaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status)
				.body(setError(status, e.getMessage(), request, "Integrity Violation."));
	}

	private StandardError setError(HttpStatus status, String errorFromMessage, HttpServletRequest request, String message) {
		StandardError error = new StandardError();
		error.setTimesTamp(Instant.now());
		error.setStatus(status.value());
		error.setError(message);
		error.setMessage(errorFromMessage);
		error.setPath(request.getRequestURI());
		return error;
	}

}
