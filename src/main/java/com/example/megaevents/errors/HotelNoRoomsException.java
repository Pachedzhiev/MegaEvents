package com.example.megaevents.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Arguments are wrong")
public class HotelNoRoomsException extends RuntimeException {
    public HotelNoRoomsException(String message) {
        super(message);
    }
}

