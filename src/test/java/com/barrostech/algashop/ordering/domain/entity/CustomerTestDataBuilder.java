package com.barrostech.algashop.ordering.domain.entity;

import com.barrostech.algashop.ordering.domain.valueobject.*;
import com.barrostech.algashop.ordering.domain.valueobject.id.CustomerId;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTestDataBuilder {

    private CustomerTestDataBuilder() {
        // Private constructor to prevent instantiation
    }

    public static Customer.BrandNewCustomerBuid brandNewCustomer() {
        return Customer.brandNew()
                .fullName(new FullName("John", "Doe"))
                .birthDate(LocalDate.of(1991, 7, 5))
                .email(new Email("john.doe@email.com"))
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
                                .build());
    }

    public static Customer.ExistingCustomerBuild existingCustomer() {
        return Customer.existing()
                .id(new CustomerId())
                .registeredAt(OffsetDateTime.now())
                .promotionNotificationsAllowed(true)
                .archived(false)
                .archivedAt(null)
                .fullName(new FullName("John", "Doe"))
                .birthDate(LocalDate.of(1991, 7, 5))
                .email(new Email("johndoe@email.com"))
                .phone(new Phone("478-256-2604"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(true)
                .loyaltyPoints(LoyaltPoints.ZERO)
                .address(Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build())
                ;
    }

    public static Customer.ExistingCustomerBuild existingAnonymizedCustomer() {
        return Customer.existing()
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
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());
    }
}