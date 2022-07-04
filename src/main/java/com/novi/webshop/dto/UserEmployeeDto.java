package com.novi.webshop.dto;

import com.novi.webshop.model.Orders;
import com.novi.webshop.model.ReturnCart;
import com.novi.webshop.model.ShoppingCart;

import java.util.List;

public class UserEmployeeDto extends UserDto {
    private Long id;
    private String emailAddress;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private List<ReturnsDto> returnsDtoList;

    private List<OrderDto> orderDtoList;

    private List<OrderDto> finishedOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ReturnsDto> getReturnCartDtoList() {
        return returnsDtoList;
    }

    public void setReturnCartDtoList(List<ReturnsDto> returnsDtoList) {
        this.returnsDtoList = returnsDtoList;
    }

    public List<OrderDto> getOrderDtoList() {
        return orderDtoList;
    }

    public void setOrderDtoList(List<OrderDto> orderDtoList) {
        this.orderDtoList = orderDtoList;
    }

    public List<OrderDto> getFinishedOrders() {
        return finishedOrders;
    }

    public void setFinishedOrders(List<OrderDto> finishedOrders) {
        this.finishedOrders = finishedOrders;
    }
}
