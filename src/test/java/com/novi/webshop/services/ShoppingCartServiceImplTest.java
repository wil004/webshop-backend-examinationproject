package com.novi.webshop.services;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Product;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.ProductRepository;
import com.novi.webshop.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ShoppingCartServiceImplTest {

    @Autowired
    CustomerService customerService;
    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ProductService productService;

    /* If I keep going to test an application without the knowledge of relational testing, I will spend the
     rest of my examination time just testing... I will not be able to finish this application like this,
    for that reason I will stop testing. (I already spend more than 33% of my examination time on testing in total)!
     googling and trying.*/


    @Test
    void createShoppingCard() {
    }

    @Test
    void deleteShoppingCart() {
    }

    @Test
    void transferToShoppingCart() {
    }
}