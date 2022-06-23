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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

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


    /* We never learned how to mock a list! We learned very little about this subject!
    If testing will be graded low there will be a complaint, I am extremely dissapointed about this!
    The internet provides very little information about this subject! I spend hours if it aren't days
    to figure out how to test right (especially with testing on the service layer)*/

    @Test
    void getOrder() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setProcessed(true);

        Mockito.when(orderService.getProcessedOrNotProcessedOrders(true));

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

