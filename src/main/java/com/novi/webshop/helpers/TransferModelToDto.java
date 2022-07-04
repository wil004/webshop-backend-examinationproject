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
                OrderDto orderDto = TransferModelToDto.transferToOrderDto(customer.getOrderHistory().get(i));

               orderHistoryList.add(orderDto);
               orderHistoryList.get(i).setCustomerDto(null);
            }

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
        if(shoppingCart.getQuantityAndProductList() != null) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for(int i = 0; i < shoppingCart.getQuantityAndProductList().size(); i++) {
                productDtoList.add(transferToProductDto(shoppingCart.getQuantityAndProductList().get(i).getProduct()));
                productDtoList.get(i).setAmountOfProducts(shoppingCart.getQuantityAndProductList().get(i).getAmountOfProducts());
            }
            shoppingCartDto.setProductList(productDtoList);
        }
        return shoppingCartDto;
    }

    public static ReturnsDto transferToReturnCartDto(Returns returns) {
        ReturnsDto returnsDto = new ReturnsDto();
        returnsDto.setId(returns.getId());
        returnsDto.setTotalPrice(returns.getTotalPrice());
        returnsDto.setProcessed(returns.isProcessed());
        returnsDto.setBankAccountForReturn(returns.getBankAccountForReturn());
        returnsDto.setOrderDto(TransferModelToDto.transferToOrderDto(returns.getCustomerOrder()));
        if(returns.getQuantityAndProductList() != null) {
            List<ProductDto> returnCartDtoProductList = new ArrayList<>();
            for (int i = 0; i < returns.getQuantityAndProductList().size(); i++) {
                returnCartDtoProductList.add(transferToProductDto(returns.getQuantityAndProductList().get(i).getProduct()));
                returnCartDtoProductList.get(i).setAmountOfProducts(returns.getQuantityAndProductList().get(i).getAmountOfProducts());
                returnCartDtoProductList.get(i).setAmountOfReturningProducts(returns.getQuantityAndProductList().get(i).getAmountOfReturningProducts());
            }
            returnsDto.setReturnProductList(returnCartDtoProductList);
        }
        return returnsDto;
    }

    public static ProductDto transferToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setCategory(product.getCategory());
        productDto.setSellingPrice(product.getSellingPrice());
        productDto.setRetailPrice(product.getRetailPrice());
        productDto.setAmountOfProducts(product.getAmountOfProducts());
        productDto.setAmountOfReturningProducts(product.getAmountOfReturningProducts());
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
            // To prevent a StackOverFlowError I did not call the transferToCustomerDto method!
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(order.getCustomer().getId());
            customerDto.setEmailAddress(order.getCustomer().getEmailAddress());
            customerDto.setUsername(order.getCustomer().getUsername());
            customerDto.setFirstName(order.getCustomer().getFirstName());
            customerDto.setLastName(order.getCustomer().getLastName());
            customerDto.setStreetName(order.getCustomer().getStreetName());
            customerDto.setHouseNumber(order.getCustomer().getHouseNumber());
            customerDto.setAdditionalToHouseNumber(order.getCustomer().getAdditionalToHouseNumber());
            customerDto.setCity(order.getCustomer().getCity());
            customerDto.setZipcode(order.getCustomer().getZipcode());
            orderDto.setCustomerDto(customerDto);
        }

        if(order.getQuantityAndProductList() != null) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for (int i = 0; i < order.getQuantityAndProductList().size(); i++) {
                productDtoList.add(transferToProductDto(order.getQuantityAndProductList().get(i).getProduct()));
                productDtoList.get(i).setAmountOfProducts(order.getQuantityAndProductList().get(i).getAmountOfProducts());
                productDtoList.get(i).setAmountOfReturningProducts(order.getQuantityAndProductList().get(i).getAmountOfReturningProducts());

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

            List<ReturnsDto> returnsDtoList = new ArrayList<>();
            for (int i = 0; i < employee.getReturnCartList().size(); i++) {
                returnsDtoList.add(TransferModelToDto.transferToReturnCartDto(employee.getReturnCartList().get(i)));
            }
            userEmployeeDto.setReturnCartDtoList(returnsDtoList);

            List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
            for (int i = 0; i < employee.getReturnCartList().size(); i++) {
                returnCartDtoList.add(TransferModelToDto.transferToReturnCartDto(employee.getReturnCartList().get(i)));
            }
            userEmployeeDto.setReturnCartDtoList(returnCartDtoList);

        }
        return userEmployeeDto;
    }
}
