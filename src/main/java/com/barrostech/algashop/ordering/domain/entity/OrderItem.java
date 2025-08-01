package com.barrostech.algashop.ordering.domain.entity;

import com.barrostech.algashop.ordering.domain.valueobject.Money;
import com.barrostech.algashop.ordering.domain.valueobject.ProductName;
import com.barrostech.algashop.ordering.domain.valueobject.Quantity;
import com.barrostech.algashop.ordering.domain.valueobject.id.OrderId;
import com.barrostech.algashop.ordering.domain.valueobject.id.OrderItemId;
import com.barrostech.algashop.ordering.domain.valueobject.id.ProductId;
import lombok.Builder;

import java.util.Objects;

public class OrderItem {

    private OrderItemId id;
    private OrderId orderId;

    private ProductId productId;
    private ProductName productName;

    private Money price;
    private Quantity quantity;

    private Money totalAmount;

    @Builder(builderClassName = "OrderItemBuilder", builderMethodName = "existem")
    public OrderItem(OrderItemId id, OrderId orderId, ProductId productId, ProductName productName,
                     Money price, Quantity quantity, Money totalAmount) {
        setId(id);
        setOrderId(orderId);
        setProductId(productId);
        setProductName(productName);
        setPrice(price);
        setQuantity(quantity);
        setTotalAmount(totalAmount);
    }


    @Builder(builderClassName = "BrandNewOrderItemBuilder", builderMethodName = "brandNew")
    private static OrderItem createBrandNew(OrderId orderId, ProductId productId, ProductName productName,
                     Money price, Quantity quantity) {
        OrderItem orderItem = new OrderItem(
                new OrderItemId(),
                orderId,
                productId,
                productName,
                price,
                quantity,
                Money.ZERO
        );

        orderItem.recalculateTotals();
        return orderItem;

    }

    public OrderItemId id() {
        return id;
    }

    public OrderId orderId() {
        return orderId;
    }

    public ProductId productId() {
        return productId;
    }

    public ProductName productName() {
        return productName;
    }

    public Money price() {
        return price;
    }

    public Quantity quantity() {
        return quantity;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    private void recalculateTotals() {
        this.setTotalAmount(this.price.multiply(this.quantity));
    }

    private void setId(OrderItemId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setOrderId(OrderId orderId) {
        Objects.requireNonNull(orderId);
        this.orderId = orderId;
    }

    private void setProductId(ProductId productId) {
        Objects.requireNonNull(productId);
        this.productId = productId;
    }

    private void setProductName(ProductName productName) {
        Objects.requireNonNull(productName);
        this.productName = productName;
    }

    private void setPrice(Money price) {
        Objects.requireNonNull(price);
        this.price = price;
    }

    private void setQuantity(Quantity quantity) {
        Objects.requireNonNull(quantity);
        this.quantity = quantity;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
