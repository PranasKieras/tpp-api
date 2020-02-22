package com.exception;

import com.sun.istack.NotNull;

public class BadRequestDataException extends TppException {

    public BadRequestDataException(@NotNull final String message){
        super(message, 400);
    }
}
