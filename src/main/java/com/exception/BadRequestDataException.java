package com.exception;

import lombok.NonNull;

public class BadRequestDataException extends TppException {

    public BadRequestDataException(@NonNull final String message){
        super(message, 400);
    }
}
