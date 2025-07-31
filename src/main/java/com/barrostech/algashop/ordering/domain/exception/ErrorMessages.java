package com.barrostech.algashop.ordering.domain.exception;

public class ErrorMessages {

    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid. Please provide a valid email address.";
    public static final String VALIDATION_ERROR_FULLNAME_IS_NULL = "FullName cannot be null";
    public static final String VALIDATION_ERROR_FULLNAME_IS_BLANK = "FullName cannot be blank";
    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";

    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is archived and cannot be modified or deleted.";

    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGED = "Cannot change order status from %s to %s. Current status: %s.";

    public static final String ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_PAST = "Expected delivery date cannot be in the past. Current date: %s";

}
