package com.barrostech.algashop.ordering.domain.validator;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class FieldValidations {

    private FieldValidations() {
    }

    public static void requiresNonBlank(String field) {
        requiresNonBlank(field, "Field cannot be blank.");
    }

    public static void requiresNonBlank(String field, String errorMessage) {
        Objects.requireNonNull(field, errorMessage);
        if (field.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void requiresValidEmail(String email){
        requiresValidEmail(email, null);
    }

    public static void requiresValidEmail(String email, String errorMessage) {
        Objects.requireNonNull(email,errorMessage);
        if(email.isBlank()){
            throw new IllegalArgumentException(errorMessage);
        }
        if(!EmailValidator.getInstance().isValid(email)){
            throw new  IllegalArgumentException(errorMessage);
        }
    }


}
