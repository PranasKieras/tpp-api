package com.exception;

public class TppAppConfigurationException extends Exception {
    public TppAppConfigurationException(String message, Exception e) {
        super(message, e);
    }
}
