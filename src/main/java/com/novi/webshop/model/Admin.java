package com.novi.webshop.model;

import org.springframework.stereotype.Component;

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

    private String role;
    @OneToMany(mappedBy = "adminForUnProcessedList")
    private List<ShoppingCart> allNotProcessedShoppingCarts;
    @OneToMany(mappedBy = "adminForProcessedList")
    private List<ShoppingCart> allProcessedShoppingCarts;
    @OneToMany(mappedBy = "adminForUnProcessedList")
    private List<ReturnCart> allNotProcessedReturnCarts;
    @OneToMany(mappedBy = "adminForProcessedList")
    private List<ReturnCart> allProcessedReturnCarts;

    @OneToMany(mappedBy = "admin")
    private List<Employee> employees;

    public Admin() {
        this.role = "ADMIN";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ShoppingCart> getAllNotProcessedShoppingCarts() {
        return allNotProcessedShoppingCarts;
    }

    public void setAllNotProcessedShoppingCarts(List<ShoppingCart> allNotProcessedShoppingCarts) {
        this.allNotProcessedShoppingCarts = allNotProcessedShoppingCarts;
    }

    public List<ShoppingCart> getAllProcessedShoppingCarts() {
        return allProcessedShoppingCarts;
    }

    public void setAllProcessedShoppingCarts(List<ShoppingCart> allProcessedShoppingCarts) {
        this.allProcessedShoppingCarts = allProcessedShoppingCarts;
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