package com.barrostech.algashop.ordering.domain.valueobject;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class LoyaltPointsTest {

    @Test
    void given_negativePoints_whenCreateLoyaltyPoints_shouldThrowException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new LoyaltPoints(-1));
    }

    @Test
    void given_zeroPoints_whenCreateLoyaltyPoints_shouldNotThrowException() {
        LoyaltPoints points = new LoyaltPoints(0);
        assertThat(points.value()).isEqualTo(0);
    }

    @Test
    void given_positivePoints_whenCreateLoyaltyPoints_shouldNotThrowException() {
        LoyaltPoints points = new LoyaltPoints(10);

        assertThat(points.add(5).value()).isEqualTo(15);
    }

    @Test
    void given_loyaltPoints_when_toString_then_returnStringValue() {
        LoyaltPoints points = new LoyaltPoints(42);
        assertThat(points.toString()).isEqualTo("42");
    }
}