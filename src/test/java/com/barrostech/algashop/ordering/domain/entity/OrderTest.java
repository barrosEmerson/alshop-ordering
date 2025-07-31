package com.barrostech.algashop.ordering.domain.entity;


import com.barrostech.algashop.ordering.domain.exception.OrderInvalidExpectedDeliveryDateException;
import com.barrostech.algashop.ordering.domain.exception.OrderStatusCannotBeCnangedException;
import com.barrostech.algashop.ordering.domain.valueobject.*;
import com.barrostech.algashop.ordering.domain.valueobject.id.CustomerId;
import com.barrostech.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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

    @Test
    public void shouldCalculateTotals(){
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();
        order.addItem(
                productId,
                new ProductName("Test Product"),
                new Money("100"),
                new Quantity(2)
        );

        order.addItem(
                productId,
                new ProductName("Test Product2"),
                new Money("50"),
                new Quantity(1)
        );


        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("250"));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(3));
    }

    @Test
    public void givenDrafOrder_whenPlace_shouldChangeStatusToPlaced() {
        Order order = Order.draft(new CustomerId());
        order.place();

        Assertions.assertThat(order.isPlaced()).isTrue();
    }

    @Test
    public void givenPlacedOrder_whenPlace_shouldThrowException() {
        Order order = Order.draft(new CustomerId());
        order.place();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeCnangedException.class)
                .isThrownBy(order::place);
    }

    @Test
    public void givenDraftOrder_whenChangeBillingInfo_shouldAllowChange() {
        Address address = Address.builder()
                .street("Bourbon Street")
                .number("1234")
                .neighborhood("North Ville")
                .complement("apt. 11")
                .city("Montfort")
                .state("South Carolina")
                .zipCode(new ZipCode("79911")).build();

        BillingInfo billingInfo = BillingInfo.builder()
                .address(address)
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .fullName(new FullName("John", "Doe"))
                .build();

        Order order = Order.draft(new CustomerId());
        order.changeBilling(billingInfo);

        BillingInfo expectedBillingInfo = BillingInfo.builder()
                .address(address)
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .fullName(new FullName("John", "Doe"))
                .build();

        Assertions.assertThat(order.billing()).isEqualTo(expectedBillingInfo);
    }

    @Test
    public void givenDraftOrder_whenChangeShippingInfo_shouldAllowChange() {
        Address address = Address.builder()
                .street("Bourbon Street")
                .number("1234")
                .neighborhood("North Ville")
                .complement("apt. 11")
                .city("Montfort")
                .state("South Carolina")
                .zipCode(new ZipCode("79911")).build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .address(address)
                .fullName(new FullName("John", "Doe"))
                .document(new Document("112-33-2321"))
                .phone(new Phone("111-441-1244"))
                .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;
        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(1);

        order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate);

        Assertions.assertWith(order,
                o -> Assertions.assertThat(o.shipping()).isEqualTo(shippingInfo),
                o -> Assertions.assertThat(o.shippingCost()).isEqualTo(shippingCost),
                o -> Assertions.assertThat(o.expectedDeliveryDate()).isEqualTo(expectedDeliveryDate)
        );

    }

    @Test
    public void givenDraftOrderAndDeliveryDateInThePast_whenChangeShippingInfo_shouldNotAllowChange() {
        Address address = Address.builder()
                .street("Bourbon Street")
                .number("1234")
                .neighborhood("North Ville")
                .complement("apt. 11")
                .city("Montfort")
                .state("South Carolina")
                .zipCode(new ZipCode("79911")).build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .address(address)
                .fullName(new FullName("John", "Doe"))
                .document(new Document("112-33-2321"))
                .phone(new Phone("111-441-1244"))
                .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;

        LocalDate expectedDeliveryDate = LocalDate.now().minusDays(2);

        Assertions.assertThatExceptionOfType(OrderInvalidExpectedDeliveryDateException.class)
                .isThrownBy(()-> order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate));
    }

}