package com.novi.webshop.controller;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.services.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    @Autowired
    public CustomerController(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerServiceImpl.getAllCustomers());
    }

    @GetMapping("/id={id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerServiceImpl.getCustomerById(id));
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        final URI location = URI.create("/customer" + customerDto.getId());
        return ResponseEntity.created(location).body(customerServiceImpl.createCustomer(customerDto));
    }
}
