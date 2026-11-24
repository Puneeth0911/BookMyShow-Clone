package com.alpha.theaterService.exception;

public class ShowNotFoundException extends RuntimeException {

	public ShowNotFoundException() {
		super("Show id not found");
	}
	
    public ShowNotFoundException(String message) {
        super(message);
    }
}
