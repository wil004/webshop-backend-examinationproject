package com.novi.webshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ReturnCart {

    @Id
    @GeneratedValue
    private Long id;


    private double totalPrice;

    private boolean processed;
    @ManyToOne
    private ShoppingCart shoppingCart;

    @ManyToMany
    private List<Product> returnProductList;

    @ManyToOne
    private Admin adminForProcessedList;
    @ManyToOne
    private Admin adminForUnProcessedList;

    @ManyToOne
    private Employee employeeReturnCartList;


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

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<Product> getReturnProductList() {
        return returnProductList;
    }

    public void setReturnProductList(List<Product> productList) {
        this.returnProductList = productList;
    }

    public Admin getAdminForProcessedList() {
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

    public Employee getEmployeeReturnCartList() {
        return employeeReturnCartList;
    }

    public void setEmployeeReturnCartList(Employee employeeReturnCartList) {
        this.employeeReturnCartList = employeeReturnCartList;
    }
}
