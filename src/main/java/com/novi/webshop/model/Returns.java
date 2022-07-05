package com.novi.webshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Returns {

    @Id
    @GeneratedValue
    private Long id;


    private double totalPrice;

    private boolean processed;

    private String bankAccountForReturn;

    @OneToMany(mappedBy = "returns")
    List<QuantityAndProduct> quantityAndProductList;

    @ManyToOne
    private Orders customerOrder;

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
