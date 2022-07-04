package com.novi.webshop.helpers;

import com.novi.webshop.dto.*;
import com.novi.webshop.model.*;

import java.util.ArrayList;
import java.util.List;

public class TransferModelToDto {

    public static CustomerDto transferToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setEmailAddress(customer.getEmailAddress());
        customerDto.setUsername(customer.getUsername());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setStreetName(customer.getStreetName());
        customerDto.setHouseNumber(customer.getHouseNumber());
        customerDto.setAdditionalToHouseNumber(customer.getAdditionalToHouseNumber());
        customerDto.setCity(customer.getCity());
        customerDto.setZipcode(customer.getZipcode());
        if(customer.getShoppingCart() != null) {
            customerDto.setShoppingCartDto(transferToShoppingCartDto(customer.getShoppingCart()));
        }
        if(customer.getOrderHistory() != null) {
            List<OrderDto> orderHistoryList = new ArrayList<>();
                for (int i = 0; i < customer.getOrderHistory().size(); i++) {
                    customer.getOrderHistory().get(i).setCustomer(null);
                    orderHistoryList.add(TransferModelToDto.transferToOrderDto(customer.getOrderHistory().get(i)));
                }
            customerDto.setOrderHistoryDto(orderHistoryList);
        }

        return customerDto;
    }


    public static ShoppingCartDto transferToShoppingCartDto(ShoppingCart shoppingCart) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(shoppingCart.getId());
        shoppingCartDto.setTotalPrice(shoppingCart.getTotalPrice());
        if(shoppingCart.getProductList() != null) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for(int i = 0; i < shoppingCart.getProductList().size(); i++) {
                productDtoList.add(transferToProductDto(shoppingCart.getProductList().get(i)));
            }
            shoppingCartDto.setProductList(productDtoList);
        }
        return shoppingCartDto;
    }

    public static ReturnCartDto transferToReturnCartDto(ReturnCart returnCart) {
        ReturnCartDto returnCartDto = new ReturnCartDto();
        returnCartDto.setId(returnCart.getId());
        returnCartDto.setTotalPrice(returnCart.getTotalPrice());
        returnCartDto.setProcessed(returnCart.isProcessed());
        returnCartDto.setOrderDto(TransferModelToDto.transferToOrderDto(returnCart.getCustomerOrder()));
        if(returnCart.getReturnProductList() != null) {
            List<ProductDto> returnCartDtoProductList = new ArrayList<>();
            for (int i = 0; i < returnCart.getReturnProductList().size(); i++) {
                returnCartDtoProductList.add(transferToProductDto(returnCart.getReturnProductList().get(i)));
            }
            returnCartDto.setReturnProductList(returnCartDtoProductList);
        }
        return returnCartDto;
    }

    public static ProductDto transferToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setCategory(product.getCategory());
        productDto.setSellingPrice(product.getSellingPrice());
        productDto.setRetailPrice(product.getRetailPrice());
        productDto.setAmountOfOrderedProducts(product.getAmountOfOrderedProducts());
        productDto.setProductPictureUrl(product.getProductPictureUrl());
        return productDto;
    }

    public static OrderDto transferToOrderDto(Orders order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setProcessed(order.isProcessed());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setPaid(order.isPaid());
        if(order.getCustomer() != null) {
            orderDto.setCustomerDto(TransferModelToDto.transferToCustomerDto(order.getCustomer()));
            orderDto.getCustomerDto().setShoppingCartDto(null);
            orderDto.getCustomerDto().setOrderHistoryDto(null);
        }
        if(order.getProductList() != null) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (int i = 0; i < order.getProductList().size(); i++) {
                productDtoList.add(transferToProductDto(order.getProductList().get(i)));
            }
            orderDto.setProductDtoList(productDtoList);
        }
        return orderDto;
    }

    public static UserEmployeeDto transferToUserEmployeeDto(Employee employee) {
        UserEmployeeDto userEmployeeDto = new UserEmployeeDto();
        userEmployeeDto.setId(employee.getId());
        userEmployeeDto.setUsername(employee.getUsername());
        userEmployeeDto.setEmailAddress(employee.getEmailAddress());
        userEmployeeDto.setFirstName(employee.getFirstName());
        userEmployeeDto.setLastName(employee.getLastName());
        List<OrderDto> orderDtoList = new ArrayList<>();
        if(employee.getOrderList() != null) {
            for (int i = 0; i < employee.getOrderList().size(); i++) {
                orderDtoList.add(TransferModelToDto.transferToOrderDto(employee.getOrderList().get(i)));
            }
            userEmployeeDto.setOrderDtoList(orderDtoList);
        }
        if(employee.getFinishedOrders() != null) {
            List<OrderDto> orderDtoList2 = new ArrayList<>();
            for (int i = 0; i < employee.getFinishedOrders().size(); i++) {
                orderDtoList2.add(TransferModelToDto.transferToOrderDto(employee.getFinishedOrders().get(i)));
            }
            userEmployeeDto.setFinishedOrders(orderDtoList2);
        }
        if(employee.getReturnCartList() != null) {
            List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
            for (int i = 0; i < employee.getReturnCartList().size(); i++) {
                returnCartDtoList.add(TransferModelToDto.transferToReturnCartDto(employee.getReturnCartList().get(i)));
            }
            userEmployeeDto.setReturnCartDtoList(returnCartDtoList);
        }
        return userEmployeeDto;
    }
}
