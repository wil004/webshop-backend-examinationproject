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

    private LocalDateTime orderDate;

    private Long orderDateInMilliSeconds;

    private double totalPrice;


    @OneToMany(mappedBy = "customerOrder")
    private List<ReturnCart> returnList;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToMany
    private List<Product> productList;

    @ManyToOne
    private Employee employeeOrderList;
    @ManyToOne
    private Admin adminForUnProcessedList;
    @ManyToOne
    private Admin adminForProcessedList;

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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }


    public List<ReturnCart> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnCart> returnList) {
        this.returnList = returnList;
    }

    public Employee getEmployeeOrderList() {
        return employeeOrderList;
    }

    public void setEmployeeOrderList(Employee employeeOrderList) {
        this.employeeOrderList = employeeOrderList;
    }

    public Admin getAdminForUnProcessedList() {
        return adminForUnProcessedList;
    }

    public void setAdminForUnProcessedList(Admin adminForUnProcessedList) {
        this.adminForUnProcessedList = adminForUnProcessedList;
    }

    public Admin getAdminForProcessedList() {
        return adminForProcessedList;
    }

    public void setAdminForProcessedList(Admin adminForProcessedList) {
        this.adminForProcessedList = adminForProcessedList;
    }
}
