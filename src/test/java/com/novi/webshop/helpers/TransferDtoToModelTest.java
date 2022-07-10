package com.novi.webshop.helpers;

import com.novi.webshop.dto.*;
import com.novi.webshop.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class TransferDtoToModelTest {

    @Autowired
    private TransferDtoToModel transferDtoToModel;

    private ProductDto productDto;

    private ShoppingCartDto shoppingCartDto;

    private CustomerDto customerDto;

    private ReturnsDto returnsDto;

    private OrderDto orderDto;

    private UserEmployeeDto employeeDto;

    @BeforeEach
    void setup() {


        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setProductName("test");
        productDto.setPrice(200);
        productDto.setCategory("test-category");
        productDto.setAmountOfProducts(5);
        productDto.setAmountOfReturningProducts(2);
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(productDto);

        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(2L);
        shoppingCartDto.setProductList(productDtoList);
        shoppingCartDto.setTotalPrice(300);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(3L);
        customerDto.setEmailAddress("email@email.nl");
        customerDto.setUsername("username");
        customerDto.setPassword("password");
        customerDto.setFirstName("William");
        customerDto.setLastName("Meester");
        customerDto.setStreetName("streetName");
        customerDto.setHouseNumber(21);
        customerDto.setCity("Winschoten");
        customerDto.setZipcode("7777");


        OrderDto orderDto = new OrderDto();
        orderDto.setId(4L);
        orderDto.setCustomerDto(customerDto);
        orderDto.setProductDtoList(productDtoList);
        List<OrderDto> orderDtoList = new ArrayList<>();
        orderDtoList.add(orderDto);

        ReturnsDto returnsDto = new ReturnsDto();
        returnsDto.setId(5L);
        returnsDto.setOrderDto(orderDto);
        returnsDto.setReturnedProducts(productDtoList);
        List<ReturnsDto> returnsDtoList = new ArrayList<>();
        returnsDtoList.add(returnsDto);

        UserEmployeeDto employeeDto = new UserEmployeeDto();
        employeeDto.setId(6L);
        employeeDto.setUsername("username");
        employeeDto.setPassword("password");
        employeeDto.setOrderDtoList(orderDtoList);
        employeeDto.setFinishedOrders(orderDtoList);
        employeeDto.setReturnsDtoList(returnsDtoList);
        employeeDto.setFinishedReturns(returnsDtoList);

        this.productDto = productDto;
        this.shoppingCartDto = shoppingCartDto;
        this.customerDto = customerDto;
        this.returnsDto = returnsDto;
        this.orderDto = orderDto;
        this.employeeDto = employeeDto;
    }

    @Test
    void transferToCustomer() {
        Customer customer = transferDtoToModel.transferToCustomer(customerDto);

        assertEquals(customerDto.getZipcode(), customer.getZipcode());
    }

    @Test
    void transferToShoppingCart() {
        ShoppingCart shoppingCart = transferDtoToModel.transferToShoppingCart(shoppingCartDto);

        assertEquals(shoppingCartDto.getProductList().get(0).getProductName(), shoppingCart.getQuantityAndProductList().get(0).getProduct().getProductName());
    }

    @Test
    void transferToReturns() {
        Returns returns = transferDtoToModel.transferToReturns(returnsDto);

        assertEquals(returnsDto.getReturnedProducts().get(0).getProductName(), returns.getQuantityAndProductList().get(0).getProduct().getProductName());
    }

    @Test
    void transferToProduct() {
        Product product = transferDtoToModel.transferToProduct(productDto);

        assertEquals(productDto.getPrice(), product.getPrice());
    }

    @Test
    void transferToOrder() {
        Orders orders = transferDtoToModel.transferToOrder(orderDto);

        assertEquals(orderDto.getCustomerDto().getFirstName(), orders.getCustomer().getFirstName());
        assertEquals(orderDto.getProductDtoList().get(0).getProductName(),
                orders.getQuantityAndProductList().get(0).getProduct().getProductName());
    }

    @Test
    void transferToUserEmployeeDto() {
        Employee employee = transferDtoToModel.transferToUserEmployeeDto(employeeDto);

        assertEquals(employeeDto.getLastName(), employee.getLastName());
    }
}