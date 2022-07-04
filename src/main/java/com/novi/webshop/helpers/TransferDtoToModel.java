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
            List<QuantityAndProduct> productList = new ArrayList<>();
            for(int i = 0; i < shoppingCartDto.getProductList().size(); i++) {
                QuantityAndProduct quantityAndProduct = new QuantityAndProduct();
                quantityAndProduct.setProduct(TransferDtoToModel.transferToProduct(shoppingCartDto.getProductList().get(i)));
                quantityAndProduct.setAmountOfProducts(shoppingCartDto.getProductList().get(i).getAmountOfProducts());
                quantityAndProduct.setAmountOfReturningProducts(shoppingCartDto.getProductList().get(i).getAmountOfReturningProducts());
                productList.add(quantityAndProduct);
            }
            shoppingCart.setQuantityAndProductList(productList);
        }
        return shoppingCart;
    }

    public static Returns transferToReturnCart(ReturnsDto returnsDto) {
        Returns returns = new Returns();
        returns.setId(returns.getId());
        returns.setTotalPrice(returnsDto.getTotalPrice());
        returns.setProcessed(returnsDto.isProcessed());
        returns.setBankAccountForReturn(returnsDto.getBankAccountForReturn());
        if (returnsDto.getOrderDto() != null) {
            returns.setCustomerOrder(transferToOrder(returnsDto.getOrderDto()));
        }
        if(returnsDto.getReturnProductList() != null) {
            List<QuantityAndProduct> returnCartProductList = new ArrayList<>();
            for (int i = 0; i < returnsDto.getReturnProductList().size(); i++) {
                QuantityAndProduct quantityAndProduct = new QuantityAndProduct();
                quantityAndProduct.setProduct(TransferDtoToModel.transferToProduct(returnsDto.getReturnProductList().get(i)));
                quantityAndProduct.setAmountOfProducts(returnsDto.getReturnProductList().get(i).getAmountOfProducts());
                quantityAndProduct.setAmountOfReturningProducts(returnsDto.getReturnProductList().get(i).getAmountOfReturningProducts());
                returnCartProductList.add(quantityAndProduct);
            }
            returns.setQuantityAndProductList(returnCartProductList);
        }
        return returns;
    }

    public static Product transferToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setProductName(productDto.getProductName());
        product.setCategory(productDto.getCategory());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setRetailPrice(productDto.getRetailPrice());
        product.setAmountOfProducts(productDto.getAmountOfProducts());
        product.setAmountOfReturningProducts(productDto.getAmountOfReturningProducts());
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
            List<QuantityAndProduct> productList = new ArrayList<>();
            for (int i = 0; i < order.getQuantityAndProductList().size(); i++) {
                QuantityAndProduct quantityAndProduct = new QuantityAndProduct();
                quantityAndProduct.setProduct(TransferDtoToModel.transferToProduct(orderDto.getProductDtoList().get(i)));
                quantityAndProduct.setAmountOfProducts(orderDto.getProductDtoList().get(i).getAmountOfProducts());
                quantityAndProduct.setAmountOfReturningProducts(orderDto.getProductDtoList().get(i).getAmountOfReturningProducts());
                productList.add(quantityAndProduct);
            }
            order.setQuantityAndProductList(productList);
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
