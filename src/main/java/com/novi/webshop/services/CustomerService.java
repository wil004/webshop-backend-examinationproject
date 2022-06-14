package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Product;
import com.novi.webshop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
                Customer customer = transferToCustomer(customerDto);
                final Customer savedCustomer = customerRepository.save(customer);
                return transferToCustomerDto(savedCustomer);
            }


    protected Customer transferToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
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
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setStreetName(customer.getStreetName());
        customerDto.setHouseNumber(customer.getHouseNumber());
        customerDto.setAdditionalToHouseNumber(customer.getAdditionalToHouseNumber());
        customerDto.setCity(customer.getCity());
        customerDto.setZipcode(customer.getZipcode());
        return customerDto;
    }

}
