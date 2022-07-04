package com.novi.webshop.model;

import javax.persistence.*;

@Entity
public class QuantityAndProduct {

    @Id
    @GeneratedValue
    private Long id;

    private int amountOfProducts;
    private int amountOfReturningProducts;

    public QuantityAndProduct() {
    }

    public QuantityAndProduct(Product product, int amountOfProducts, int amountOfReturningProducts) {
        this.amountOfProducts = amountOfProducts;
        this.amountOfReturningProducts = amountOfReturningProducts;
        this.product = product;
    }


    @ManyToOne(cascade= CascadeType.ALL)
    private Product product;

    @ManyToOne(cascade= CascadeType.ALL)
    private ShoppingCart shoppingCart;

    @ManyToOne(cascade= CascadeType.ALL)
    private Orders order;

    @ManyToOne(cascade= CascadeType.ALL)
    private Returns returns;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(int amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }

    public int getAmountOfReturningProducts() {
        return amountOfReturningProducts;
    }

    public void setAmountOfReturningProducts(int amountOfReturningProducts) {
        this.amountOfReturningProducts = amountOfReturningProducts;
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

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Returns getReturns() {
        return returns;
    }

    public void setReturns(Returns returns) {
        this.returns = returns;
    }
}
