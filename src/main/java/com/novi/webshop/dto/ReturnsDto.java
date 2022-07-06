package com.novi.webshop.dto;

import java.util.List;

public class ReturnsDto {

    private Long id;

    private double totalPrice;

    private boolean processed;

    private String bankAccountForReturn;

    private OrderDto orderDto;

    private List<ProductDto> returnedProducts;

    private List<ProductDto> finishedReturnedProducts;

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

    public String getBankAccountForReturn() {
        return bankAccountForReturn;
    }

    public void setBankAccountForReturn(String bankAccountForReturn) {
        this.bankAccountForReturn = bankAccountForReturn;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public List<ProductDto> getReturnedProducts() {
        return returnedProducts;
    }

    public void setReturnedProducts(List<ProductDto> returnedProducts) {
        this.returnedProducts = returnedProducts;
    }

    public List<ProductDto> getFinishedReturnedProducts() {
        return finishedReturnedProducts;
    }

    public void setFinishedReturnedProducts(List<ProductDto> finishedReturnedProducts) {
        this.finishedReturnedProducts = finishedReturnedProducts;
    }
}

