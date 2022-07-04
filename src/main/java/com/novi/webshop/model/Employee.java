package com.novi.webshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Employee extends User{
    @Id
    @GeneratedValue
    private Long id;

    private String emailAddress;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "employeeReturnCartList")
    private List<Returns> returnsList;

    @OneToMany(mappedBy = "employeeOrderList")
    private List<Orders> orderList;

    @OneToMany(mappedBy = "employeeFinishedOrderList")
    private List<Orders> finishedOrders;


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

    public List<Returns> getReturnCartList() {
        return returnsList;
    }

    public void setReturnCartList(List<Returns> returnsList) {
        this.returnsList = returnsList;
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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
