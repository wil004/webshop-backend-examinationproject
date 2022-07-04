package com.novi.webshop.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue
    private Long id;

    private boolean processed;

    private boolean paid;


    private LocalDateTime orderDate;

    private Long orderDateInMilliSeconds;

    private double totalPrice;


    @OneToMany(mappedBy = "customerOrder")
    private List<Returns> returnsList;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "order")
    List<QuantityAndProduct> quantityAndProductList;

    @ManyToOne
    private Employee employeeOrderList;

    @ManyToOne
    private Employee employeeFinishedOrderList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getOrderDateInMilliSeconds() {
        return orderDateInMilliSeconds;
    }

    public void setOrderDateInMilliSeconds(Long orderDateInMilliSeconds) {
        this.orderDateInMilliSeconds = orderDateInMilliSeconds;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public List<QuantityAndProduct> getQuantityAndProductList() {
        return quantityAndProductList;
    }

    public void setQuantityAndProductList(List<QuantityAndProduct> quantityAndProductList) {
        this.quantityAndProductList = quantityAndProductList;
    }

    public List<Returns> getReturnList() {
        return returnsList;
    }

    public void setReturnList(List<Returns> returnsList) {
        this.returnsList = returnsList;
    }

    public Employee getEmployeeOrderList() {
        return employeeOrderList;
    }

    public void setEmployeeOrderList(Employee employeeOrderList) {
        this.employeeOrderList = employeeOrderList;
    }

    public Employee getEmployeeFinishedOrderList() {
        return employeeFinishedOrderList;
    }

    public void setEmployeeFinishedOrderList(Employee employeeFinishedOrderList) {
        this.employeeFinishedOrderList = employeeFinishedOrderList;
    }
}
