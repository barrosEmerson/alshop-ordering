package com.barrostech.algashop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void shouldCreateEmail() {


        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new Email("invalid-email"));
    }

    @Test
    void given_validEmail_when_toString_then_returnEmailValue() {
        Email email = new Email("user@email.com");
        Assertions.assertThat(email.toString()).isEqualTo("user@email.com");
    }
}