package com.barrostech.algashop.ordering.domain.entity;

import com.barrostech.algashop.ordering.domain.exception.CustomerArchivedException;
import com.barrostech.algashop.ordering.domain.validator.FieldValidations;
import com.barrostech.algashop.ordering.domain.valueobject.*;
import com.barrostech.algashop.ordering.domain.valueobject.id.CustomerId;
import lombok.Builder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.barrostech.algashop.ordering.domain.exception.ErrorMessages.*;

public class Customer {

    private CustomerId id;
    private FullName fullName;
    private LocalDate birthDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltPoints loyaltyPoints;
    private Address address;


    @Builder(builderClassName = "BrandNewCustomerBuid", builderMethodName = "brandNew")
    private static Customer createBrandNew( FullName fullName, LocalDate birthDate, Email email, Phone phone, Document document, Boolean promotionNotificationsAllowed, Address address){
        return new Customer(new CustomerId(),
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificationsAllowed,
                false,
                OffsetDateTime.now(),
                null,
                LoyaltPoints.ZERO,
                address);
    }


    @Builder(builderClassName = "ExistingCustomerBuild", builderMethodName = "existing")
    private Customer(CustomerId id, FullName fullName, LocalDate birthDate, Email email, Phone phone, Document document, Boolean promotionNotificationsAllowed,
                    Boolean archived, OffsetDateTime registeredAt, OffsetDateTime archivedAt, LoyaltPoints loyaltyPoints, Address address) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArchivedAt(archivedAt);
        this.setLoyaltyPoints(loyaltyPoints);
        this.setAddress(address);
    }



    public void addLoyaltyPoints(LoyaltPoints loyaltyPointsAdded) {
        verifyIfChangeble();

        this.setLoyaltyPoints(this.loyaltyPoints.add(loyaltyPointsAdded));
    }

    public void archive() {
        if (this.isArchived()) {
            throw new CustomerArchivedException();
        }
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("Anonymous", "Anonymous"));
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("000-00-0000"));
        this.setEmail(new Email(UUID.randomUUID() + "@anonymous.com"));
        this.setBirthDate(null);
        this.setPromotionNotificationsAllowed(false);
        this.setAddress(this.address().toBuilder()
                .complement(null)
                .number("Anonymized")
                .build());
    }

    public void enablePromotionNotifications() {
        verifyIfChangeble();
        this.setPromotionNotificationsAllowed(true);
    }

    public void diseablePromotionNotifications() {
        verifyIfChangeble();
        this.setPromotionNotificationsAllowed(false);
    }

    public void changeName(FullName fullName) {
        verifyIfChangeble();
        this.setFullName(fullName);
    }
    public void changeEmail(Email email) {
        verifyIfChangeble();
        this.setEmail(email);
    }
    public void changePhone(Phone phone) {
        verifyIfChangeble();
        this.setPhone(phone);
    }

    public void changeAddress(Address address) {
        verifyIfChangeble();
        this.setAddress(address);
    }

    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Document document() {
        return document;
    }

    public Boolean isPromotionNotificationsAllowed() {
        return promotionNotificationsAllowed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public LoyaltPoints loyaltyPoints() {
        return loyaltyPoints;
    }

    public Address address() {
        return address;
    }

    private void setId(CustomerId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, VALIDATION_ERROR_FULLNAME_IS_NULL);
        this.fullName = fullName;
    }

    private void setBirthDate(LocalDate birthDate) {

        if(birthDate == null) {
            this.birthDate = null;
            return;
        }
        if(birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
        }

        this.birthDate = birthDate;
    }

    private void setEmail(Email email) {
        FieldValidations.requiresValidEmail(email.value(), VALIDATION_ERROR_EMAIL_IS_INVALID);
    }

    private void setPhone(Phone phone) {
        Objects.requireNonNull(phone);
        this.phone = phone;
    }

    private void setDocument(Document document) {
        Objects.requireNonNull(document);
        this.document = document;
    }

    private void setPromotionNotificationsAllowed(Boolean promotionNotificationsAllowed) {
        Objects.requireNonNull(promotionNotificationsAllowed);
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
    }

    private void setArchived(Boolean archived) {

        Objects.requireNonNull(archived);
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {

        Objects.requireNonNull(registeredAt);
        this.registeredAt = registeredAt;
    }

    private void setArchivedAt(OffsetDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    private void setLoyaltyPoints(LoyaltPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        this.loyaltyPoints = loyaltyPoints;
    }

    public void setAddress(Address address) {
        Objects.requireNonNull(address);
        this.address = address;
    }

    private void verifyIfChangeble() {
        if (this.isArchived()) {
            throw new CustomerArchivedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}