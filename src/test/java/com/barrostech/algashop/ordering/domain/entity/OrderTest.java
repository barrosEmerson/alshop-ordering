package com.barrostech.algashop.ordering.domain.entity;


import com.barrostech.algashop.ordering.domain.valueobject.Money;
import com.barrostech.algashop.ordering.domain.valueobject.ProductName;
import com.barrostech.algashop.ordering.domain.valueobject.Quantity;
import com.barrostech.algashop.ordering.domain.valueobject.id.CustomerId;
import com.barrostech.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class OrderTest {

    @Test
    public void testOrderCreation() {
        Order order = Order.draft(new CustomerId());

    }

    @Test
    public void shouldAddItemToOrder() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();
        order.addItem(
                productId,
                new ProductName("Test Product"),
                new Money("100"),
                new Quantity(1)
        );

        Assertions.assertThat(order.items().size()).isEqualTo(1);

        OrderItem item = order.items().iterator().next();

        Assertions.assertWith(item,
                (i)-> Assertions.assertThat(i.id()).isNotNull(),
                (i) -> Assertions.assertThat(i.productId()).isEqualTo(productId),
                (i) -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("Test Product")),
                (i) -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
                (i) -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );

    }

    @Test
    public void shouldGeneratorException(){
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();
        order.addItem(
                productId,
                new ProductName("Test Product"),
                new Money("100"),
                new Quantity(1)
        );

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }
}