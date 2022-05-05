package com.redhat.lab.util;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.redhat.lab.entity.GeneralRes;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		GeneralRes error = new GeneralRes("500", "Validation Error", ex.getBindingResult().toString());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	private ResponseEntity<GeneralRes> handleEntityNotFound(EntityNotFoundException ex) {
		GeneralRes error = new GeneralRes("400", ex.getMessage(), null);

		return new ResponseEntity<>(error, HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	private ResponseEntity<GeneralRes> handleException(Exception ex) {
		GeneralRes error = new GeneralRes("500", ex.getMessage(), null);

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
