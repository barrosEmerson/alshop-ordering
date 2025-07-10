package com.barrostech.algashop.ordering.domain.valueobject;

import com.barrostech.algashop.ordering.domain.validator.FieldValidations;

public record Email(String value) {

    public Email(String value) {
        FieldValidations.requiresValidEmail(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
