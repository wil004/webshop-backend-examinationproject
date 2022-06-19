package com.novi.webshop.dto;

import com.novi.webshop.model.ReturnCart;
import com.novi.webshop.model.ShoppingCart;

import java.util.List;

public class UserEmployeeDto {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private List<ReturnCartDto> returnCartDtoList;

    private List<ShoppingCartDto> shoppingCartDtoList;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public List<ReturnCartDto> getReturnCartDtoList() {
        return returnCartDtoList;
    }

    public void setReturnCartDtoList(List<ReturnCartDto> returnCartDtoList) {
        this.returnCartDtoList = returnCartDtoList;
    }

    public List<ShoppingCartDto> getShoppingCartDtoList() {
        return shoppingCartDtoList;
    }

    public void setShoppingCartDtoList(List<ShoppingCartDto> shoppingCartDtoList) {
        this.shoppingCartDtoList = shoppingCartDtoList;
    }
}
