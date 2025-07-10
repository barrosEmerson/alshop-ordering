package com.barrostech.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record LoyaltPoints(Integer value) implements Comparable<LoyaltPoints> {

    public static final LoyaltPoints ZERO = new LoyaltPoints(0);

    public LoyaltPoints() {
        this(0);

    }

    public LoyaltPoints(Integer value) {
        Objects.requireNonNull(value);
        if(value < 0) {
            throw new IllegalArgumentException("Loyalty points cannot be negative.");
        }
        this.value = value;
    }

    public LoyaltPoints add(Integer pointsToAdd) {
        return add(new LoyaltPoints(pointsToAdd));
    }

    public LoyaltPoints add(LoyaltPoints pointsToAdd) {
        Objects.requireNonNull(pointsToAdd);
        if(pointsToAdd.value() <= 0){
            throw new IllegalArgumentException("Loyalty points to add cannot be negative.");
        }

        return new LoyaltPoints(this.value + pointsToAdd.value());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(LoyaltPoints o) {
        return this.value().compareTo(o.value);
    }
}
