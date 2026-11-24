package com.alpha.customerService.exception;

public class CustomerAlreadyExistWithPhoneException extends RuntimeException {

	public CustomerAlreadyExistWithPhoneException() {
		super("Customer already exist with phone number");
	}

	
}

