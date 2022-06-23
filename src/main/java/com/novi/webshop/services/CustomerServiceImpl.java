package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.helpers.TransferDtoToModel;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.*;
import com.novi.webshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserServiceImpl userServiceImpl;
    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, UserServiceImpl userServiceImpl, ShoppingCartServiceImpl shoppingCartServiceImpl) {
        this.customerRepository = customerRepository;
        this.userServiceImpl = userServiceImpl;
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> allCustomerList = new ArrayList<>(customerRepository.findAll());
        List<CustomerDto> allCustomerDtoList = new ArrayList<>();
        for (Customer customer : allCustomerList) {
            allCustomerDtoList.add(TransferModelToDto.transferToCustomerDto(customer));
        }
        return allCustomerDtoList;
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        if(customerRepository.findById(id).isPresent()) {
            return TransferModelToDto.transferToCustomerDto(customer);
        } else {
            throw new RecordNotFoundException("Couldn't find customer");
        }
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        if(customerDto.getUsername() != null && customerDto.getPassword() != null) {
           return createCustomerAccount(customerDto);
        } else {
            return createCustomerGuest(customerDto);
        }
    }
    @Override
    public CustomerDto createCustomerAccount(CustomerDto customerDto) {
                Customer customer = TransferDtoToModel.transferToCustomer(customerDto);
                if (!userServiceImpl.doesUsernameAlreadyExist(customer.getUsername())) {
                    if(customer.getUsername() != null && customer.getPassword() != null && customer.getEmailAddress() != null) {
                        Customer savedCustomer = customerRepository.save(customer);
                        shoppingCartServiceImpl.createShoppingCard(savedCustomer.getId());
                        return TransferModelToDto.transferToCustomerDto(savedCustomer);
                    }
                    else {
                        throw new RecordNotFoundException("You need a username password and email-address to make an account!");
                    }
        } else {
                    throw new RecordNotFoundException("Username already exists!");
        }
            }

    @Override
    public CustomerDto createCustomerGuest(CustomerDto customerDto) {
        Customer customer = TransferDtoToModel.transferToCustomer(customerDto);
        final Customer savedCustomer = customerRepository.save(customer);
        return TransferModelToDto.transferToCustomerDto(savedCustomer);
    }

}
