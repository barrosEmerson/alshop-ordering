package com.barrostech.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record FullName(String firstName, String lastName) {

    public FullName(String firstName, String lastName) {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);

        if(firstName.isBlank()){
            throw new IllegalArgumentException("First name cannot be blank.");
        }
        if(lastName.isBlank()){
            throw new IllegalArgumentException("Last name cannot be blank.");
        }

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
