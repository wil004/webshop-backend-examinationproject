package com.novi.webshop.helpers;

import com.novi.webshop.dto.*;
import com.novi.webshop.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class TransferModelToDtoTest {

    @Autowired
    private TransferModelToDto transferModelToDto;

    private Product product;

    private ShoppingCart shoppingCart;

    private Customer customer;

    private Returns returns;

    private Orders order;

    private Employee employee;

    @BeforeEach
    void setup() {
        Product product = new Product();
        product.setId(1L);
        product.setProductName("test");
        product.setPrice(200);
        product.setCategory("test-category");

        List<QuantityAndProduct> quantityAndProductList = new ArrayList<>();
        QuantityAndProduct quantityAndProduct = new QuantityAndProduct();
        quantityAndProduct.setProduct(product);
        quantityAndProduct.setAmountOfProducts(5);
        quantityAndProductList.add(quantityAndProduct);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(2L);
        shoppingCart.setQuantityAndProductList(quantityAndProductList);

        Customer customer = new Customer();
        customer.setId(3L);
        customer.setEmailAddress("email@email.nl");
        customer.setUsername("username");
        customer.setPassword("password");
        customer.setFirstName("William");
        customer.setLastName("Meester");
        customer.setStreetName("streetName");
        customer.setHouseNumber(21);
        customer.setCity("Winschoten");
        customer.setZipcode("7777");
        customer.setShoppingCart(shoppingCart);


        Orders order = new Orders();
        order.setId(4L);
        order.setCustomer(customer);
        order.setQuantityAndProductList(quantityAndProductList);
        List<Orders> orderList = new ArrayList<>();
        orderList.add(order);
        customer.setOrderHistory(orderList);

        Returns returns = new Returns();
        returns.setId(5L);
        returns.setCustomerOrder(order);
        returns.setQuantityAndProductList(quantityAndProductList);
        List<Returns> returnsList = new ArrayList<>();
        returnsList.add(returns);

        Employee employee = new Employee();
        employee.setId(6L);
        employee.setUsername("username");
        employee.setPassword("password");
        employee.setOrderList(orderList);
        employee.setFinishedOrders(orderList);
        employee.setReturnsList(returnsList);
        employee.setFinishedReturns(returnsList);

        this.product = product;
        this.shoppingCart = shoppingCart;
        this.customer = customer;
        this.returns = returns;
        this.order = order;
        this.employee = employee;
    }

    @Test
    void transferToCustomerDto() {
        CustomerDto customerDto = transferModelToDto.transferToCustomerDto(customer);

        assertEquals(customer.getFirstName(), customerDto.getFirstName());
        assertEquals(customer.getShoppingCart().getId(), customerDto.getShoppingCartDto().getId());
        assertEquals(customer.getOrderHistory().get(0).getQuantityAndProductList().get(0).getProduct().getProductName(), customerDto.getOrderHistoryDto().get(0).getProductDtoList().get(0).getProductName());
    }

    @Test
    void transferToShoppingCartDto() {
        ShoppingCartDto shoppingCartDto = transferModelToDto.transferToShoppingCartDto(shoppingCart);

        assertEquals(shoppingCart.getQuantityAndProductList().get(0).getProduct().getProductName(), shoppingCartDto.getProductList().get(0).getProductName());
    }

    @Test
    void transferToReturnsDto() {
        ReturnsDto returnsDto = transferModelToDto.transferToReturnsDto(returns);

        assertEquals(returns.getCustomerOrder().getCustomer().getFirstName(), returnsDto.getOrderDto().getCustomerDto().getFirstName());
        assertEquals(returns.getQuantityAndProductList().size(), returnsDto.getReturnedProducts().size());
    }

    @Test
    void transferToProductDto() {
        ProductDto productDto = transferModelToDto.transferToProductDto(product);

        assertEquals(product.getPrice(), productDto.getPrice());
    }

    @Test
    void transferToOrderDto() {
        OrderDto orderDto = transferModelToDto.transferToOrderDto(order);

        assertEquals(order.getCustomer().getZipcode(), orderDto.getCustomerDto().getZipcode());
        assertEquals(order.getQuantityAndProductList().size(), orderDto.getProductDtoList().size());
    }

    @Test
    void transferToUserEmployeeDto() {
        UserEmployeeDto userEmployeeDto = transferModelToDto.transferToUserEmployeeDto(employee);

        assertEquals(employee.getOrderList().size(), userEmployeeDto.getOrderDtoList().size());
        assertEquals(employee.getFinishedReturns().size(), userEmployeeDto.getFinishedReturns().size());
        assertEquals(employee.getFirstName(), userEmployeeDto.getFirstName());
    }
}