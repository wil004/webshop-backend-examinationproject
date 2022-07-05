package com.novi.webshop.controller;

import com.novi.webshop.dto.ProductDto;
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
class ProductControllerTest {

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
        AttachmentService attachmentService;

        @MockBean
        UserServiceImpl userService;

        @MockBean
        AdminServiceImpl adminService;

        @MockBean
        UserDetailsService userDetailsService;

        @MockBean
        JwtService jwtService;

        @MockBean
        EmployeeServiceImpl employeeService;

        @MockBean
        PDFGeneratorService pdfGeneratorService;


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

        @MockBean
        AttachmentRepository attachmentRepository;


        @Test
        @WithMockUser
        void getProduct() throws Exception {
            ProductDto productDto = new ProductDto();
            productDto.setId(1L);
            productDto.setProductName("test");

            Mockito.when(productService.getProductByName("test")).thenReturn(productDto);

            this.mockMvc
                    .perform(MockMvcRequestBuilders.get("/product/name/test"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.productName", is("test")));
        }
}