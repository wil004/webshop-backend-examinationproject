package com.novi.webshop.services;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.helpers.TransferDtoToModel;
import com.novi.webshop.model.Customer;
import com.novi.webshop.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CustomerServiceImplTest {

    @Autowired
    private TransferDtoToModel transferDtoToModel;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeAll
    void setup() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmailAddress("email@email.nl");
        customerDto.setFirstName("William");
        customerDto.setLastName("Meester");
        customerDto.setStreetName("streetName");
        customerDto.setHouseNumber(21);
        customerDto.setCity("Winschoten");
        customerDto.setZipcode("7777");
        Customer customer = transferDtoToModel.transferToCustomer(customerDto);

        customerRepository.save(customer);
    }

    @Test
    void createCustomer() {
        Customer createdCustomerInBeforeAll = customerRepository.findAll().get(0);
        assertEquals("William", createdCustomerInBeforeAll.getFirstName());
    }

    @Test
    void createCustomerAccount() {
        Customer customer = customerRepository.findAll().get(0);

        customer.setId(null);
        customer.setUsername("username");
        customer.setPassword("password");
        customerRepository.save(customer);

        assertEquals("username", customerRepository.findAll().get(1).getUsername());
    }

    @Test
    void createCustomerGuest() {
        Customer createdGuestCustomerInBeforeAll = customerRepository.findAll().get(0);
        assertEquals("William", createdGuestCustomerInBeforeAll.getFirstName());
    }

    @Test
    void getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();

        assertEquals(2, allCustomers.size());
    }

    @Test
    void getCustomerById() {
        Customer aCustomer = customerRepository.findAll().get(0);
        Customer actualCustomer = customerRepository.findById(aCustomer.getId()).orElseThrow();

        assertEquals(aCustomer.getFirstName(), actualCustomer.getFirstName());
    }

}