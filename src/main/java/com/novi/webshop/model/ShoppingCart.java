package com.novi.webshop.model;
import javax.persistence.*;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue
    private Long id;

    private double totalPrice;

    @OneToOne
    private Customer customer;

    @OneToMany
    private List<QuantityAndProduct> quantityAndProductList;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<QuantityAndProduct> getQuantityAndProductList() {
        return quantityAndProductList;
    }

    public void setQuantityAndProductList(List<QuantityAndProduct> quantityAndProductList) {
        this.quantityAndProductList = quantityAndProductList;
    }


}

