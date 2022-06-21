package com.novi.webshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Employee extends User{
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "employeeReturnCartList")
    private List<ReturnCart> returnCartList;

    @OneToMany(mappedBy = "employeeOrderList")
    private List<Orders> orderList;

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

    public List<ReturnCart> getReturnCartList() {
        return returnCartList;
    }

    public void setReturnCartList(List<ReturnCart> returnCartList) {
        this.returnCartList = returnCartList;
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
