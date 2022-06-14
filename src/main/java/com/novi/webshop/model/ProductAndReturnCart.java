package com.novi.webshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductAndReturnCart {
    @Id
    @GeneratedValue
    private Long id;

    private int amountOfProduct;

    @ManyToOne
    private Product product;

    @ManyToOne
    private ReturnCart returnCart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmountOfProduct() {
        return amountOfProduct;
    }

    public void setAmountOfProduct(int amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ReturnCart getReturnCart() {
        return returnCart;
    }

    public void setReturnCart(ReturnCart returnCart) {
        this.returnCart = returnCart;
    }
}