package com.novi.webshop.helpers;

import com.novi.webshop.dto.*;
import com.novi.webshop.model.*;

import java.util.ArrayList;
import java.util.List;

public class TransferDtoToModel {

    public static Customer transferToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setEmailAddress(customerDto.getEmailAddress());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setStreetName(customerDto.getStreetName());
        customer.setHouseNumber(customerDto.getHouseNumber());
        customer.setAdditionalToHouseNumber(customerDto.getAdditionalToHouseNumber());
        customer.setCity(customerDto.getCity());
        customer.setZipcode(customerDto.getZipcode());
        return customer;
    }

    public static ShoppingCart transferToShoppingCart(ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartDto.getId());
        shoppingCart.setTotalPrice(shoppingCartDto.getTotalPrice());
        if(shoppingCartDto.getProductList() != null) {
            List<Product> productList = new ArrayList<>();
            for(int i = 0; i < shoppingCartDto.getProductList().size(); i++) {
                productList.add(transferToProduct(shoppingCartDto.getProductList().get(i)));
            }
            shoppingCart.setProductList(productList);
        }
        return shoppingCart;
    }

    public static ReturnCart transferToReturnCart(ReturnCartDto returnCartDto) {
        ReturnCart returnCart = new ReturnCart();
        returnCart.setId(returnCart.getId());
        returnCart.setTotalPrice(returnCartDto.getTotalPrice());
        returnCart.setProcessed(returnCartDto.isProcessed());
        if (returnCartDto.getOrderDto() != null) {
            returnCart.setCustomerOrder(transferToOrder(returnCartDto.getOrderDto()));
        }
        if(returnCartDto.getReturnProductList() != null) {
            List<Product> returnCartProductList = new ArrayList<>();
            for (int i = 0; i < returnCartDto.getReturnProductList().size(); i++) {
                returnCartProductList.add(transferToProduct(returnCartDto.getReturnProductList().get(i)));
            }
            returnCart.setReturnProductList(returnCartProductList);
        }
        return returnCart;
    }

    public static Product transferToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setProductName(productDto.getProductName());
        product.setCategory(productDto.getCategory());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setRetailPrice(productDto.getRetailPrice());
        product.setAmountOfOrderedProducts(productDto.getAmountOfOrderedProducts());
        product.setProductPictureUrl(productDto.getProductPictureUrl());
        return product;
    }

    public static Orders transferToOrder(OrderDto orderDto) {
        Orders order = new Orders();
        order.setId(orderDto.getId());
        order.setProcessed(orderDto.isProcessed());
        order.setPaid(orderDto.isPaid());
        order.setOrderDate(orderDto.getOrderDate());
        order.setTotalPrice(orderDto.getTotalPrice());
        if(orderDto.getCustomerDto() != null) {
            order.setCustomer(TransferDtoToModel.transferToCustomer(orderDto.getCustomerDto()));
        }
        if(orderDto.getProductDtoList() != null) {
            List<Product> productList = new ArrayList<>();
            for (int i = 0; i < order.getProductList().size(); i++) {
                productList.add(transferToProduct(orderDto.getProductDtoList().get(i)));
            }
            order.setProductList(productList);
        }
        return order;
    }

    public static Employee transferToUserEmployeeDto(UserEmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmailAddress(employeeDto.getEmailAddress());
        employee.setUsername(employeeDto.getUsername());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        return employee;
    }
}
