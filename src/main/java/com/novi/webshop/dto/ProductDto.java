package com.novi.webshop.dto;

public class ProductDto {

    private Long id;
    private String product;
    private String category;

    private double sellingPrice;
    private double retailPrice;
    private int amountOfProducts;
    private int amountOfReturningProducts;

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

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
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
}
