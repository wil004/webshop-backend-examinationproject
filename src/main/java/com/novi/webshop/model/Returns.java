package com.novi.webshop.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Returns {

    @Id
    @GeneratedValue
    private Long id;


    private double totalPrice;

    private boolean processed;

    @NotNull
    private String bankAccountForReturn;

    @OneToMany(mappedBy = "returns")
    List<QuantityAndProduct> quantityAndProductList;

    @ManyToOne
    private Orders customerOrder;

    @ManyToOne
    private Employee employeeReturnsList;

    @ManyToOne
    private Employee employeeFinishedReturnsList;


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

    public String getBankAccountForReturn() {
        return bankAccountForReturn;
    }

    public void setBankAccountForReturn(String bankAccountForReturn) {
        this.bankAccountForReturn = bankAccountForReturn;
    }

    public List<QuantityAndProduct> getQuantityAndProductList() {
        return quantityAndProductList;
    }

    public void setQuantityAndProductList(List<QuantityAndProduct> quantityAndProductList) {
        this.quantityAndProductList = quantityAndProductList;
    }

    public Orders getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(Orders customerOrder) {
        this.customerOrder = customerOrder;
    }

    public Employee getEmployeeReturnsList() {
        return employeeReturnsList;
    }

    public void setEmployeeReturnsList(Employee employeeReturnsList) {
        this.employeeReturnsList = employeeReturnsList;
    }

    public Employee getEmployeeFinishedReturnsList() {
        return employeeFinishedReturnsList;
    }

    public void setEmployeeFinishedReturnsList(Employee employeeFinishedReturnsList) {
        this.employeeFinishedReturnsList = employeeFinishedReturnsList;
    }
}
