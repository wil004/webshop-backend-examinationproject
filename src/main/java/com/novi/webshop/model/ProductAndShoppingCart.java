package com.novi.webshop.model;

import com.novi.webshop.services.ShoppingCartService;

import javax.persistence.*;

@Entity
public class ProductAndShoppingCart {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private ShoppingCart shoppingCart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
