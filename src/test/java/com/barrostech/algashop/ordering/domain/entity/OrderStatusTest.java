package com.barrostech.algashop.ordering.domain.entity;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderStatusTest {


    @Test
    void canChangeTo(){
        Assertions.assertThat(OrderStatus.DRAFT.canChangeTo(OrderStatus.PLACED)).isTrue();
        Assertions.assertThat(OrderStatus.DRAFT.canChangeTo(OrderStatus.CANCELLED)).isTrue();
        Assertions.assertThat(OrderStatus.PAID.canChangeTo(OrderStatus.DRAFT)).isFalse();
    }

    @Test
    void canNotChangeTo(){
        Assertions.assertThat(OrderStatus.DRAFT.canNotChangeTo(OrderStatus.PLACED)).isFalse();
        Assertions.assertThat(OrderStatus.DRAFT.canNotChangeTo(OrderStatus.CANCELLED)).isFalse();
        Assertions.assertThat(OrderStatus.PAID.canNotChangeTo(OrderStatus.DRAFT)).isTrue();
    }
}