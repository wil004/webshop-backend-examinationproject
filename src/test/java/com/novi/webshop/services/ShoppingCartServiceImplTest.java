package com.novi.webshop.services;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.helpers.TransferDtoToModel;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Product;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.ProductRepository;
import com.novi.webshop.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ShoppingCartServiceImplTest {
    @Autowired
    private TransferDtoToModel transferDtoToModel;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;


    @BeforeAll
    void setup() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setEmailAddress("email@email.nl");
        customerDto.setUsername("username111");
        customerDto.setPassword("password");
        customerDto.setFirstName("William");
        customerDto.setLastName("Meester");
        customerDto.setStreetName("streetName");
        customerDto.setHouseNumber(21);
        customerDto.setCity("Winschoten");
        customerDto.setZipcode("7777");

        Customer customer = transferDtoToModel.transferToCustomer(customerDto);
        customerRepository.save(customer);
        ProductDto productDto = new ProductDto();
        productDto.setProductName("test");
        productDto.setPrice(200);
        productDto.setCategory("test");
        productService.createProduct(productDto);

    }

    @Test
    void createShoppingCard() {
        shoppingCartService.createShoppingCard(customerRepository.findAll().get(0).getId());

        ShoppingCart createdShoppingCart = shoppingCartRepository.findAll().get(0);

        assertEquals(shoppingCartRepository.findAll().get(0).getId(), createdShoppingCart.getId());
    }
}