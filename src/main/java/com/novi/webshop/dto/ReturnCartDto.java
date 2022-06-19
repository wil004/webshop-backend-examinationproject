package com.novi.webshop.dto;

import java.util.List;

public class ReturnCartDto {

    private Long id;

    private double totalPrice;

    private boolean processed;
    private ShoppingCartDto shoppingCartDto;

    private List<ProductDto> returnProductList;

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

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public ShoppingCartDto getShoppingCartDto() {
        return shoppingCartDto;
    }

    public void setShoppingCartDto(ShoppingCartDto shoppingCartDto) {
        this.shoppingCartDto = shoppingCartDto;
    }

    public List<ProductDto> getReturnProductList() {
        return returnProductList;
    }

    public void setReturnProductList(List<ProductDto> returnProductList) {
        this.returnProductList = returnProductList;
    }
}

