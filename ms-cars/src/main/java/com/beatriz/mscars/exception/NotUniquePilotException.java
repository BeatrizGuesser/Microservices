package com.beatriz.mscars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotUniquePilotException extends RuntimeException{
    public NotUniquePilotException(String message){
        super(message);
    }
}
