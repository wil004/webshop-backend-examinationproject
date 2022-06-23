package com.novi.webshop.controller;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.repository.*;
import com.novi.webshop.security.JwtService;
import com.novi.webshop.services.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;

@WebMvcTest
public class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;


    @MockBean
    CustomerServiceImpl customerService;

    @MockBean
    OrderServiceImpl orderService;

    @MockBean
    ProductServiceImpl productService;

    @MockBean
    ReturnCartServiceImpl returnCartService;

    @MockBean
    ShoppingCartServiceImpl shoppingCartService;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    AdminServiceImpl adminService;

    @MockBean
    UserDetailsService userDetailsService;

    @MockBean
    JwtService jwtService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ReturnCartRepository returnCartRepository;

    @MockBean
    ShoppingCartRepository shoppingCartRepository;

    @MockBean
    AdminRepository adminRepository;

    @MockBean
    EmployeeRepository employeeRepository;


    @Test
    @WithMockUser
    void getCustomer() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setUsername("test");

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customerDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customer/id=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", is("test")));
    }
}
