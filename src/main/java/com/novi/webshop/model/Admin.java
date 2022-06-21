package com.novi.webshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
public class Admin extends User {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "adminForUnProcessedList")
    private List<Orders> allNotProcessedOrders;
    @OneToMany(mappedBy = "adminForProcessedList")
    private List<Orders> allProcessedOrders;

    @OneToMany(mappedBy = "adminForUnProcessedList")
    private List<ReturnCart> allNotProcessedReturnCarts;
    @OneToMany(mappedBy = "adminForProcessedList")
    private List<ReturnCart> allProcessedReturnCarts;

    @OneToMany(mappedBy = "admin")
    private List<Employee> employees;

    public Admin() {
        super.setRole("ADMIN");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Orders> getAllNotProcessedOrders() {
        return allNotProcessedOrders;
    }

    public void setAllNotProcessedOrders(List<Orders> allNotProcessedOrders) {
        this.allNotProcessedOrders = allNotProcessedOrders;
    }

    public List<Orders> getAllProcessedOrders() {
        return allProcessedOrders;
    }

    public void setAllProcessedOrders(List<Orders> allProcessedOrders) {
        this.allProcessedOrders = allProcessedOrders;
    }

    public List<ReturnCart> getAllNotProcessedReturnCarts() {
        return allNotProcessedReturnCarts;
    }

    public void setAllNotProcessedReturnCarts(List<ReturnCart> allNotProcessedReturnCarts) {
        this.allNotProcessedReturnCarts = allNotProcessedReturnCarts;
    }

    public List<ReturnCart> getAllProcessedReturnCarts() {
        return allProcessedReturnCarts;
    }

    public void setAllProcessedReturnCarts(List<ReturnCart> allProcessedReturnCarts) {
        this.allProcessedReturnCarts = allProcessedReturnCarts;
    }
}