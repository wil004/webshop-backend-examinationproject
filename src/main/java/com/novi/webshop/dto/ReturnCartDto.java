package com.novi.webshop.dto;

import java.util.List;

public class ReturnCartDto {

    private Long id;

    private double totalPrice;

    private boolean processed;

    private OrderDto orderDto;

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

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public List<ProductDto> getReturnProductList() {
        return returnProductList;
    }

    public void setReturnProductList(List<ProductDto> returnProductList) {
        this.returnProductList = returnProductList;
    }
}

