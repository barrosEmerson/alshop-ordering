package com.barrostech.algashop.ordering.domain.entity;


import com.barrostech.algashop.ordering.domain.exception.CustomerArchivedException;
import com.barrostech.algashop.ordering.domain.valueobject.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CustomerTest {


    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    new Customer(
                            new CustomerId(),
                            new FullName("John", "Doe"),
                            LocalDate.of(1991, 7, 5),
                            new Email("invalid"),
                            new Phone("1234567890"),
                            new Document("12345678901"),
                            false,
                            OffsetDateTime.now()
                    );
                });

    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomer_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 5),
                new Email("john.doe@email.com"),
                new Phone("1234567890"),
                new Document("12345678901"),
                false,
                OffsetDateTime.now()
        );

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> {
                  customer.changeEmail(new Email("invalid"));
                });

    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 5),
                new Email("john.doe@email.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now()
        );

        customer.archive();

        Assertions.assertWith(customer,
                c -> assertThat(c.fullName().firstName()).isEqualTo("Anonymous"),
                c -> assertThat(c.fullName().lastName()).isEqualTo("Anonymous"),
                c -> assertThat(c.email()).isNotEqualTo("john.doe@gmail.com"),
                c -> assertThat(c.phone().value()).isEqualTo("000-000-0000"),
                c -> assertThat(c.document().value()).isEqualTo("000-00-0000"),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse()
        );

    }

    @Test
    public void given_archivedCustomer_whenArchiveTryUpdate_shouldThrowException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("Anonymous", "Anonymous"),
                null,
                new Email("anonymous@anonymous.com"),
                new Phone("000-000-0000"),
                new Document("000-00-0000"),
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                new LoyaltPoints(10)
        );

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("email@email.com")));


        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone(new Phone("000-000-0001")));

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.diseablePromotionNotifications());

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.enablePromotionNotifications());

    }

    @Test
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 5),
                new Email("email@email.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now()
        );

        customer.addLoyaltyPoints(new LoyaltPoints(10));
        customer.addLoyaltyPoints(new LoyaltPoints(20));

        Assertions.assertWith(customer,
                c -> assertThat(c.loyaltyPoints()).isEqualTo(new LoyaltPoints(30)));

    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 5),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now()
        );


        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltPoints(0)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltPoints(-10)));


    }
}