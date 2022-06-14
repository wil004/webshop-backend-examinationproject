package com.novi.webshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue
    private Long id;

    private double totalPrice;

    private boolean processed;

    @ManyToOne(optional = false)
    private Customer customer;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ProductAndShoppingCart> productAndShoppingCarts;


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
}
