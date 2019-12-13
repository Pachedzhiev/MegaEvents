package com.example.megaevents.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "No such hotel")
public class HotelNotFoundException extends RuntimeException{
    public HotelNotFoundException(String message) {
        super(message);
    }
}