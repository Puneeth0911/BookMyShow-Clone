package com.alpha.customerService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.alpha.customerService.dto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerAlreadyExistWithPhoneException.class)
	public ResponseEntity<ResponseStructure<String>> handleCustomerAlreadyExist(
			CustomerAlreadyExistWithPhoneException ex) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setStatuscode(HttpStatus.CONFLICT.value());
		responseStructure.setMessage(ex.getMessage());
		responseStructure.setData(null);
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleCustomerNotFound(CustomerNotFoundException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setStatuscode(HttpStatus.NOT_FOUND.value());
		response.setMessage(ex.getMessage());
		response.setData(null);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SeatNotAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleSeatNotAvailable(SeatNotAvailableException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setStatuscode(HttpStatus.CONFLICT.value());
		response.setMessage(ex.getMessage());
		response.setData(null);

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(HttpClientErrorException.Conflict.class)
	public ResponseEntity<ResponseStructure<String>> handleConflict(HttpClientErrorException.Conflict ex) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setStatuscode(HttpStatus.CONFLICT.value());
		response.setMessage("Seat already selected by another customer");
		response.setData(null);

		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseStructure<String>> handleRuntimeException(RuntimeException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setStatuscode(HttpStatus.NOT_FOUND.value());
		response.setMessage(ex.getMessage());
		response.setData(null);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ShowNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleShowNotFound(ShowNotFoundException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setStatuscode(HttpStatus.NOT_FOUND.value());
		response.setMessage(ex.getMessage());
		response.setData(null);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SeatNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleSeatNotFound(SeatNotFoundException ex) {
		ResponseStructure<String> response = new ResponseStructure<>();

		response.setStatuscode(HttpStatus.NOT_FOUND.value());
		response.setMessage(ex.getMessage());
		response.setData(null);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TicketNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleTicketNotFound(TicketNotFoundException ex) {

		ResponseStructure<String> response = new ResponseStructure<>();

		response.setStatuscode(HttpStatus.NOT_FOUND.value());
		response.setMessage(ex.getMessage());
		response.setData(null);

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}