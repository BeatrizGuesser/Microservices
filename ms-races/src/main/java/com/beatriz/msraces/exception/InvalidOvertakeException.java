package com.beatriz.msraces.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidOvertakeException extends RuntimeException{
    public InvalidOvertakeException(String message){
        super(message);
    }
}
