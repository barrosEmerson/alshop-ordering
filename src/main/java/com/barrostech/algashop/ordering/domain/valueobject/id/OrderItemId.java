package com.barrostech.algashop.ordering.domain.valueobject.id;

import com.barrostech.algashop.ordering.domain.utility.IdGenerator;
import io.hypersistence.tsid.TSID;

import java.util.Objects;

public record OrderItemId(TSID value) {


    public OrderItemId {
        Objects.requireNonNull(value);
    }

    public OrderItemId(Long value) {
        this(TSID.from(value));
    }

    public OrderItemId() {
        this(IdGenerator.generateTSID());
    }


    public OrderItemId(String value) {
        this(TSID.from(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
