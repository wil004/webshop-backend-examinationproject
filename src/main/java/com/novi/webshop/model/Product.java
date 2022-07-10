package com.novi.webshop.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String productName;

    private String category;

    @NotNull
    private double price;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
