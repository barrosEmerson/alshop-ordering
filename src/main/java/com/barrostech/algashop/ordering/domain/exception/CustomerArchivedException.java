package com.barrostech.algashop.ordering.domain.exception;

import static com.barrostech.algashop.ordering.domain.exception.ErrorMessages.ERROR_CUSTOMER_ARCHIVED;

public class CustomerArchivedException extends DomainException{
    public CustomerArchivedException() {
        super(ERROR_CUSTOMER_ARCHIVED);
    }

    public CustomerArchivedException(String message, Throwable cause) {
        super(ERROR_CUSTOMER_ARCHIVED, cause);
    }
}
