package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.model.*;
import com.novi.webshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, UserService userService, ShoppingCartService shoppingCartService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> allCustomerList = new ArrayList<>(customerRepository.findAll());
        List<CustomerDto> allCustomerDtoList = new ArrayList<>();
        for (Customer customer : allCustomerList) {
            allCustomerDtoList.add(transferToCustomerDto(customer));
        }
        return allCustomerDtoList;
    }


    public CustomerDto createCustomer(CustomerDto customerDto) {
        if(customerDto.getUsername() != null && customerDto.getPassword() != null) {
           return createCustomerAccount(customerDto);
        } else {
            return createCustomerGuest(customerDto);
        }
    }
    public CustomerDto createCustomerAccount(CustomerDto customerDto) {
                Customer customer = transferToCustomer(customerDto);
                if (!userService.doesUsernameAlreadyExist(customer.getUsername())) {
                    if(customer.getUsername() != null && customer.getPassword() != null && customer.getEmailAddress() != null) {
                        Customer savedCustomer = customerRepository.save(customer);
                        shoppingCartService.createShoppingCard(savedCustomer.getId());
                        return transferToCustomerDto(savedCustomer);
                    }
                    else {
                        throw new RecordNotFoundException("You need a username password and email-address to make an account!");
                    }
        } else {
                    throw new RecordNotFoundException("Username already exists!");
        }
            }

    public CustomerDto createCustomerGuest(CustomerDto customerDto) {
        Customer customer = transferToCustomer(customerDto);
        final Customer savedCustomer = customerRepository.save(customer);
        return transferToCustomerDto(savedCustomer);
    }

    protected Customer transferToCustomer(CustomerDto customerDto) {
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

    protected CustomerDto transferToCustomerDto(Customer customer) {
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
            customerDto.setShoppingCartDto(shoppingCartService.transferToShoppingCartDto(customer.getShoppingCart()));
        }
        return customerDto;
    }
}
