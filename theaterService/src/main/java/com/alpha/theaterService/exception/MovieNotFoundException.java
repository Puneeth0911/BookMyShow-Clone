package com.alpha.theaterService.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException{

	public MovieNotFoundException() {
		super("Movie not found");
	}
	
}
