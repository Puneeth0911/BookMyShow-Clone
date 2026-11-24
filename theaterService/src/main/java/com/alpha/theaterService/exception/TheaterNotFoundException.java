package com.alpha.theaterService.exception;

public class TheaterNotFoundException extends RuntimeException {

    public TheaterNotFoundException() {
        super("Theater ID not found");
    }
}