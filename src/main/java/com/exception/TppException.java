package com.exception;

import lombok.Getter;

public class TppException extends Exception{

    @Getter
    private final int code;

    public TppException(String message, int code){
        super(message);
        this.code = code;
    }
}
