package com.novi.webshop.services;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.OrderDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long id);

    List<OrderDto> getCustomerOrderHistory(Long id);
    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto createCustomerAccount(CustomerDto customerDto);

    CustomerDto createCustomerGuest(CustomerDto customerDto);
}
