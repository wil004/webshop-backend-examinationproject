package com.novi.webshop.controller;

import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.repository.*;
import com.novi.webshop.security.JwtService;
import com.novi.webshop.services.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;

@WebMvcTest
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;


    @MockBean
    OrderServiceImpl orderService;
    @MockBean
    CustomerServiceImpl customerService;

    @MockBean
    ProductServiceImpl productService;

    @MockBean
    ReturnsServiceImpl returnCartService;

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
    ReturnsRepository returnsRepository;

    @MockBean
    ShoppingCartRepository shoppingCartRepository;

    @MockBean
    AdminRepository adminRepository;

    @MockBean
    EmployeeRepository employeeRepository;


    @Test
    void getOrderById() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setProcessed(false);

        Mockito.when(orderService.getOrderById(1L)).thenReturn(orderDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/order/id=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.processed", is(false)));
    }


    @Test
    void getOrder() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setProcessed(true);

        Mockito.when(orderService.getAllProcessedOrNotProcessedOrders(true));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/order/processed-status=true"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.processed", is(true)));
    }


    @Test
    void changeProcessedStatus() throws Exception {

      OrderDto orderDto = new OrderDto();
       orderDto.setId(1L);
      orderDto.setProcessed(true);

        Mockito.when(orderService.changeProcessedStatus(1L, false)).thenReturn(orderDto);

       this.mockMvc
                .perform(MockMvcRequestBuilders.put("/order/change-processed-status=false/id=1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
               .andExpect(MockMvcResultMatchers.jsonPath("$.processed", is(false)));
    }


}

