package com.barrostech.algashop.ordering.domain.entity;


import com.barrostech.algashop.ordering.domain.valueobject.Money;
import com.barrostech.algashop.ordering.domain.valueobject.ProductName;
import com.barrostech.algashop.ordering.domain.valueobject.Quantity;
import com.barrostech.algashop.ordering.domain.valueobject.id.OrderId;
import com.barrostech.algashop.ordering.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

class OrderItemTest {


    @Test
    public void testOrderItemCreation() {
        OrderItem.brandNew()
                .orderId(new OrderId())
                .productId(new ProductId())
                .productName(new ProductName("Test Product"))
                .price(new Money("100"))
                .quantity(new Quantity(1))
        ;
    }
}