package com.novi.webshop.dto;

import org.springframework.core.io.Resource;

import java.util.List;

public class ProductDto {

    private Long id;
    private String productName;
    private String category;

    private double sellingPrice;
    private double retailPrice;
    private int amountOfOrderedProducts;
    private int amountOfReturningProducts;

    private String productPictureUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getAmountOfOrderedProducts() {
        return amountOfOrderedProducts;
    }

    public void setAmountOfOrderedProducts(int amountOfOrderedProducts) {
        this.amountOfOrderedProducts = amountOfOrderedProducts;
    }

    public int getAmountOfReturningProducts() {
        return amountOfReturningProducts;
    }

    public void setAmountOfReturningProducts(int amountOfReturningProducts) {
        this.amountOfReturningProducts = amountOfReturningProducts;
    }

    public String getProductPictureUrl() {
        return productPictureUrl;
    }

    public void setProductPictureUrl(String productPictureUrl) {
        this.productPictureUrl = productPictureUrl;
    }
}
