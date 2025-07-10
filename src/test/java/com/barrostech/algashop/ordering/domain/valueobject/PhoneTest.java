package com.barrostech.algashop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void given_blankPhone_whenTryCreatePhone_shouldGenerateException() {

        org.assertj.core.api.Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    new Phone("");
                });
    }
    @Test
    void given_setPhoneNumber_whenIsCorrect_shouldCreatePhone() {

        Phone phone = new Phone("123456789");
        Assertions.assertThat(phone.value()).isEqualTo("123456789");
    }

    @Test
    void given_validPhone_when_toString_then_returnPhoneValue() {
        Phone phone = new Phone("123456789");
        Assertions.assertThat(phone.toString()).isEqualTo("123456789");
    }
}