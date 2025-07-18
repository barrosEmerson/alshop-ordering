package com.barrostech.algashop.ordering.domain.entity;


import com.barrostech.algashop.ordering.domain.exception.CustomerArchivedException;
import com.barrostech.algashop.ordering.domain.valueobject.*;
import com.barrostech.algashop.ordering.domain.valueobject.id.CustomerId;
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
                .isThrownBy(() -> Customer.brandNew()
                                .fullName(new FullName("John", "Doe"))
                                .birthDate(LocalDate.of(1991, 7, 5))
                                .email(new Email("invalid"))
                                .phone(new Phone("1234567890"))
                                .document(new Document("12345678901"))
                                .promotionNotificationsAllowed(false)
                                .address(
                                        Address.builder()
                                                .street("123 Main St")
                                                .complement("Apt 4B")
                                                .neighborhood("Downtown")
                                                .number("123")
                                                .city("Springfield")
                                                .state("IL")
                                                .zipCode(new ZipCode("62701"))
                                                .build()
                                )
                );

    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomer_shouldGenerateException() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> {
                  customer.changeEmail(new Email("invalid"));
                });

    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        customer.archive();

        Assertions.assertWith(customer,
                c -> assertThat(c.fullName().firstName()).isEqualTo("Anonymous"),
                c -> assertThat(c.fullName().lastName()).isEqualTo("Anonymous"),
                c -> assertThat(c.email()).isNotEqualTo("john.doe@gmail.com"),
                c -> assertThat(c.phone().value()).isEqualTo("000-000-0000"),
                c -> assertThat(c.document().value()).isEqualTo("000-00-0000"),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
                c -> assertThat(c.address()).isEqualTo(Address.builder()
                        .street("123 Main St")
                        .complement(null)
                        .neighborhood("Downtown")
                        .number("Anonymized")
                        .city("Springfield")
                        .state("IL")
                        .zipCode(new ZipCode("62701"))
                        .build())
        );

    }

    @Test
    public void given_archivedCustomer_whenArchiveTryUpdate_shouldThrowException() {
        Customer customer =  Customer.existing()
                .id(new CustomerId())
                .fullName(new FullName("Anonymous", "Anonymous"))
                .birthDate(null)
                .email(new Email("anonymous@anonymous.com"))
                .phone(new Phone("000-000-0000"))
                .document(new Document("000-00-0000"))
                .promotionNotificationsAllowed(false)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(new LoyaltPoints(10))

                .address(Address.builder()
                        .street("123 Main St")
                        .complement("Apt 4B")
                        .neighborhood("Downtown")
                        .number("123")
                        .city("Springfield")
                        .state("IL")
                        .zipCode(new ZipCode("62701"))
                        .build()).build();

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
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        customer.addLoyaltyPoints(new LoyaltPoints(10));
        customer.addLoyaltyPoints(new LoyaltPoints(20));

        Assertions.assertWith(customer,
                c -> assertThat(c.loyaltyPoints()).isEqualTo(new LoyaltPoints(30)));

    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltPoints(0)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltPoints(-10)));


    }
}