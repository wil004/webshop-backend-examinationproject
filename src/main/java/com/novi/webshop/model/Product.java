package com.novi.webshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String productName;
    private String category;

    private int amountOfOrderedProducts;

    private int amountOfReturningProducts;

    private double sellingPrice;
    private double retailPrice;

    @ManyToMany
    private List<ShoppingCart> shoppingCartList;

    @ManyToMany
    private List<Orders> orderList;

    @ManyToMany
    private List<ReturnCart> returnCartList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String product) {
        this.productName = product;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmountOfOrderedProducts() {
        return amountOfOrderedProducts;
    }

    public void setAmountOfOrderedProducts(int amountOfProducts) {
        this.amountOfOrderedProducts = amountOfProducts;
    }

    public int getAmountOfReturningProducts() {
        return amountOfReturningProducts;
    }

    public void setAmountOfReturningProducts(int amountOfReturningProducts) {
        this.amountOfReturningProducts = amountOfReturningProducts;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double buyingPrice) {
        this.retailPrice = buyingPrice;
    }

    public List<ShoppingCart> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<ShoppingCart> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    public List<ReturnCart> getReturnCartList() {
        return returnCartList;
    }

    public void setReturnCartList(List<ReturnCart> returnCartList) {
        this.returnCartList = returnCartList;
    }
}
