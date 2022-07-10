package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.helpers.TransferDtoToModel;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.*;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserServiceImpl userServiceImpl;
    private final ShoppingCartServiceImpl shoppingCartServiceImpl;
    private final TransferModelToDto transferModelToDto;
    private final TransferDtoToModel transferDtoToModel;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, UserServiceImpl userServiceImpl, ShoppingCartServiceImpl shoppingCartServiceImpl, TransferModelToDto transferModelToDto, TransferDtoToModel transferDtoToModel) {
        this.customerRepository = customerRepository;
        this.userServiceImpl = userServiceImpl;
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
        this.transferModelToDto = transferModelToDto;
        this.transferDtoToModel = transferDtoToModel;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> allCustomerList = new ArrayList<>(customerRepository.findAll());
        List<CustomerDto> allCustomerDtoList = new ArrayList<>();
        for (Customer customer : allCustomerList) {
            allCustomerDtoList.add(transferModelToDto.transferToCustomerDto(customer));
        }
        return allCustomerDtoList;
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        if(!Objects.equals(id, userServiceImpl.findIdFromUsername(JwtRequestFilter.getUsername())) &&
                !Objects.equals(userServiceImpl.findRoleFromUsername(JwtRequestFilter.getUsername()), "ADMIN") &&
                !Objects.equals(userServiceImpl.findRoleFromUsername(JwtRequestFilter.getUsername()), "EMPLOYEE")) {
            throw new RecordNotFoundException("Customer has only acces to his own data");
        }
        Customer customer = customerRepository.findById(id).orElseThrow();
        if(customerRepository.findById(id).isPresent()) {
            return transferModelToDto.transferToCustomerDto(customer);
        } else {
            throw new RecordNotFoundException("Couldn't find customer");
        }
    }

    @Override
    public List<OrderDto> getCustomerOrderHistory(Long id) {
        if(!Objects.equals(id, userServiceImpl.findIdFromUsername(JwtRequestFilter.getUsername())) && userServiceImpl.findRoleFromUsername(JwtRequestFilter.getUsername()) != "ADMIN" &&
                userServiceImpl.findRoleFromUsername(JwtRequestFilter.getUsername()) != "EMPLOYEE") {
                throw new RecordNotFoundException("Customer has only acces to his own data");
        }
        Customer customer = customerRepository.findById(id).orElseThrow();
        List<OrderDto> orderDtoList = new ArrayList<>();
        if(customerRepository.findById(id).isPresent()) {
            for (int i = 0; i < customer.getOrderHistory().size(); i++) {
                orderDtoList.add(transferModelToDto.transferToOrderDto(customer.getOrderHistory().get(i)));
            }
        }
        return orderDtoList;
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
                Customer customer = transferDtoToModel.transferToCustomer(customerDto);
                    if(customer.getUsername() != null && customer.getPassword() != null) {
                        if (!userServiceImpl.doesUsernameAlreadyExist(customer.getUsername())) {
                        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        String password = passwordEncoder.encode(customerDto.getPassword());
                        customerDto.setPassword(password);
                        customer.setPassword(customerDto.getPassword());
                        Customer savedCustomer = customerRepository.save(customer);
                        shoppingCartServiceImpl.createShoppingCard(savedCustomer.getId());
                        return transferModelToDto.transferToCustomerDto(savedCustomer);
                    } else {
                            throw new RecordNotFoundException("Username already exists!");
                        }
                    } else {
                        throw new RecordNotFoundException("You need a username and a password to make an account!");
                    }
            }

    @Override
    public CustomerDto createCustomerGuest(CustomerDto customerDto) {
        Customer customer = transferDtoToModel.transferToCustomer(customerDto);
        final Customer savedCustomer = customerRepository.save(customer);
        return transferModelToDto.transferToCustomerDto(savedCustomer);
    }

}
