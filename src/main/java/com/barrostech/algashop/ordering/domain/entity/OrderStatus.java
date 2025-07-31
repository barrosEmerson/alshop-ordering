package com.barrostech.algashop.ordering.domain.entity;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {

    DRAFT,
    PLACED(DRAFT),
    PAID(PLACED),
    READY(PAID),
    CANCELLED(PAID, READY, PLACED, DRAFT);

    OrderStatus(OrderStatus... previousStatuses) {
        this.previousStatuses = Arrays.asList(previousStatuses);
    }

    private final List<OrderStatus> previousStatuses;

    public Boolean canChangeTo(OrderStatus newStatus) {
        OrderStatus currentStatus = this;
        return newStatus.previousStatuses.contains(currentStatus);
    }

    public Boolean canNotChangeTo(OrderStatus newStatus) {
        return !canChangeTo(newStatus);
    }
}
