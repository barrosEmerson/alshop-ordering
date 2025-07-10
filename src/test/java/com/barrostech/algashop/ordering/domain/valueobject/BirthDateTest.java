package com.barrostech.algashop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class BirthDateTest {

    @Test
    void given_showAge_when_birthdateIsValid_then_returnCorrectAge() {
        // Arrange
        BirthDate birthDate = new BirthDate(LocalDate.of(2000, 1, 1));

        Assertions.assertThat(birthDate.age()).isEqualTo(25);


    }

    @Test
    void given_showAge_when_birthdateIsFuture_then_throwException(){
        LocalDate futureDate = LocalDate.now().plusDays(1);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new BirthDate(futureDate))
                .withMessage("Birth date cannot be in the future.");

    }

    @Test
    void given_birthDate_when_toString_then_returnFormattedDate() {
        BirthDate birthDate = new BirthDate(LocalDate.of(1995, 5, 20));
        Assertions.assertThat(birthDate.toString()).isEqualTo("1995-05-20");
    }

}