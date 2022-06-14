package com.novi.webshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String product;
    private String category;

    private double sellingPrice;
    private double retailPrice;

    @OneToMany(mappedBy = "product")
    private List<ProductAndShoppingCart> productAndShoppingCarts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public void setRetailPrice(double buyingPrice) {
        this.retailPrice = buyingPrice;
    }

    public List<ProductAndShoppingCart> getProductAndShoppingCarts() {
        return productAndShoppingCarts;
    }

    public void setProductAndShoppingCarts(List<ProductAndShoppingCart> productAndShoppingCarts) {
        this.productAndShoppingCarts = productAndShoppingCarts;
    }
}
