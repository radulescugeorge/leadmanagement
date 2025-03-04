package com.example.leadmanagement.exceptionhandlers;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message){
        super(message);
    }
}
