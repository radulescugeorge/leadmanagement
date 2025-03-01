package com.example.leadmanagement.exception_handlers;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message){
        super(message);
    }
}
