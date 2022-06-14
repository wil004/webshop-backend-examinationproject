package com.novi.webshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ReturnCart {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "returnCart")
    private List<ProductAndReturnCart> productAndReturnCartList;

    @ManyToOne
    private ShoppingCart shoppingCart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductAndReturnCart> getProductAndReturnCartList() {
        return productAndReturnCartList;
    }

    public void setProductAndReturnCartList(List<ProductAndReturnCart> productAndReturnCartList) {
        this.productAndReturnCartList = productAndReturnCartList;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
