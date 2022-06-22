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
            shoppingCartDto.setProduct(productDtoList);
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
        return productDto;
    }

    public static OrderDto transferToOrderDto(Orders order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setProcessed(order.isProcessed());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setCustomerDto(TransferModelToDto.transferToCustomerDto(order.getCustomer()));
        orderDto.getCustomerDto().setShoppingCartDto(null);
        List<ProductDto> productDtoList = new ArrayList<>();
        for (int i = 0; i < order.getProductList().size(); i++) {
            productDtoList.add(transferToProductDto(order.getProductList().get(i)));
        }
        orderDto.setProductDtoList(productDtoList);
        return orderDto;
    }

    public static UserEmployeeDto transferToUserEmployeeDto(Employee employee) {
        UserEmployeeDto userEmployeeDto = new UserEmployeeDto();
        userEmployeeDto.setEmailAddress(employee.getEmailAddress());
        userEmployeeDto.setFirstName(employee.getFirstName());
        userEmployeeDto.setLastName(employee.getLastName());
        return userEmployeeDto;
    }
}
