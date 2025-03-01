package com.example.leadmanagement.global_validator;

import com.example.leadmanagement.exception_handlers.InvalidDataException;

public class GlobalValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+$";
    private static final String PHONE_REGEX = "^(07)[0-9]{8}$";

    public static boolean isValidEmailGV(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static boolean isValidPhoneGV(String phone) {
        return phone != null && phone.matches(PHONE_REGEX);
    }

}