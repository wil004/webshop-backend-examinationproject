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

    private int amountOfProducts;

    private int amountOfReturningProducts;

    private double sellingPrice;
    private double retailPrice;


    private String productPictureUrl;

    @OneToMany(mappedBy = "product")
    private List<QuantityAndProduct> quantityAndProductList;


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

    public String getProductPictureUrl() {
        return productPictureUrl;
    }

    public void setProductPictureUrl(String productPictureUrl) {
        this.productPictureUrl = productPictureUrl;
    }

    public List<QuantityAndProduct> getQuantityAndProductList() {
        return quantityAndProductList;
    }

    public void setQuantityAndProductList(List<QuantityAndProduct> quantityAndProductList) {
        this.quantityAndProductList = quantityAndProductList;
    }

}
