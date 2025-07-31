package com.barrostech.algashop.ordering.domain.exception;

import com.barrostech.algashop.ordering.domain.entity.OrderStatus;
import com.barrostech.algashop.ordering.domain.valueobject.id.OrderId;

import static com.barrostech.algashop.ordering.domain.exception.ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED;

public class OrderStatusCannotBeCnangedException extends DomainException {
    public OrderStatusCannotBeCnangedException(OrderId id, OrderStatus status, OrderStatus newStatus) {
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED,id, status, newStatus));
    }
}


