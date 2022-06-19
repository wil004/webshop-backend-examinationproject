package com.novi.webshop.model;

import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue
    private Long id;

    private double totalPrice;

    private boolean processed;

    private LocalDateTime orderDate;

    private Long orderDateInMilliSeconds;

    @ManyToOne(optional = false)
    private Customer customer;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ReturnCart> returnCartList;

    @ManyToMany
    private List<Product> productList;

    @ManyToOne
    private Admin adminForProcessedList;
    @ManyToOne
    private Admin adminForUnProcessedList;

    @ManyToOne
    private Employee employeeShoppingCartList;

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


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<ReturnCart> getReturnCartList() {
        return returnCartList;
    }

    public void setReturnCartList(List<ReturnCart> returnCartList) {
        this.returnCartList = returnCartList;
    }

    public User getAdminForProcessedList() {
        return adminForProcessedList;
    }

    public void setAdminForProcessedList(Admin adminForProcessedList) {
        this.adminForProcessedList = adminForProcessedList;
    }

    public Admin getAdminForUnProcessedList() {
        return adminForUnProcessedList;
    }

    public void setAdminForUnProcessedList(Admin adminForUnProcessedList) {
        this.adminForUnProcessedList = adminForUnProcessedList;
    }

    public Employee getEmployeeShoppingCartList() {
        return employeeShoppingCartList;
    }

    public void setEmployeeShoppingCartList(Employee employeeShoppingCartList) {
        this.employeeShoppingCartList = employeeShoppingCartList;
    }
}

