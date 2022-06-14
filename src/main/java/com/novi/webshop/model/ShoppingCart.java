package com.novi.webshop.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue
    private Long id;

    private double totalPrice;

    private boolean processed;

    private LocalDateTime orderDate;

    private Long orderDateInMilliSeconds;

    @ManyToOne(optional = false)
    private Customer customer;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ProductAndShoppingCart> productAndShoppingCarts;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ReturnCart> returnProducts;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getOrderDateInMilliSeconds() {
        return orderDateInMilliSeconds;
    }

    public void setOrderDateInMilliSeconds(Long orderDateInMilliSeconds) {
        this.orderDateInMilliSeconds = orderDateInMilliSeconds;
    }

    public List<ProductAndShoppingCart> getProductAndShoppingCarts() {
        return productAndShoppingCarts;
    }

    public void setProductAndShoppingCarts(List<ProductAndShoppingCart> productAndShoppingCarts) {
        this.productAndShoppingCarts = productAndShoppingCarts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ReturnCart> getReturnProducts() {
        return returnProducts;
    }

    public void setReturnProducts(List<ReturnCart> returnProducts) {
        this.returnProducts = returnProducts;
    }
}
