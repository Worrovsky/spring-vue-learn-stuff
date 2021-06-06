package com.example.registration.validation;

public class EmailExistException extends Throwable {

    public EmailExistException(String message) {
        super(message);
    }
}
