package com.novi.webshop.model;
import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer extends User {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String emailAddress;

    private String username;
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String streetName;
    @NotNull
    private int houseNumber;

    private String additionalToHouseNumber;

    @NotNull
    private String city;
    @NotNull
    private String zipcode;

    @OneToOne
    private ShoppingCart shoppingCart;


    @OneToMany(mappedBy = "customer")
    private List<Orders> orderHistory;

    public Customer() {
        super.setRole("CUSTOMER");
    }

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

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAdditionalToHouseNumber() {
        return additionalToHouseNumber;
    }

    public void setAdditionalToHouseNumber(String additionalToHouseNumber) {
        this.additionalToHouseNumber = additionalToHouseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<Orders> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Orders> orderHistory) {
        this.orderHistory = orderHistory;
    }
}