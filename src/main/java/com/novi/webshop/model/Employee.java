package com.novi.webshop.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Employee extends User{
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String emailAddress;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @OneToMany(mappedBy = "employeeOrderList")
    private List<Orders> orderList;

    @OneToMany(mappedBy = "employeeFinishedOrderList")
    private List<Orders> finishedOrders;

    @OneToMany(mappedBy = "employeeReturnsList")
    private List<Returns> returnsList;

    @OneToMany(mappedBy = "employeeFinishedReturnsList")
    private List<Returns> finishedReturns;

    @ManyToOne
    private Admin admin;

    public Employee() {
        super.setRole("EMPLOYEE");
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

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    public List<Orders> getFinishedOrders() {
        return finishedOrders;
    }

    public void setFinishedOrders(List<Orders> finishedOrders) {
        this.finishedOrders = finishedOrders;
    }

    public List<Returns> getReturnsList() {
        return returnsList;
    }

    public void setReturnsList(List<Returns> returnsList) {
        this.returnsList = returnsList;
    }

    public List<Returns> getFinishedReturns() {
        return finishedReturns;
    }

    public void setFinishedReturns(List<Returns> finishedReturns) {
        this.finishedReturns = finishedReturns;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
