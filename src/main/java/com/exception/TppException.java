package com.exception;

import lombok.Getter;
import lombok.NonNull;

public class TppException extends Exception{

    @Getter
    private final int code;

    public TppException(@NonNull String message, int code){
        super(message);
        this.code = code;
    }

    public String getFormattedMessage() {
        return "{" +
               "\"code\":" + code + "\n" +
               "\t\"message\":" + "\"" + getMessage() + "\"\n" +
               '}';
    }
}
