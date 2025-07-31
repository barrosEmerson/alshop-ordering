package com.barrostech.algashop.ordering.domain.exception;

import com.barrostech.algashop.ordering.domain.valueobject.id.OrderId;

public class OrderInvalidExpectedDeliveryDateException extends DomainException {
    public OrderInvalidExpectedDeliveryDateException(OrderId id) {
        super(String.format(ErrorMessages.ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_PAST, id));
    }
}
