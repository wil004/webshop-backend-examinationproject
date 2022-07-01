package com.novi.webshop.helpers;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.ReturnCartDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class TransferModelToDtoTest {
    @Test
    void checksIfCustomerChangesToCustomerDto() {
        // Arrange
        Customer customer = new Customer();
        customer.setUsername("test");

        // Act
        CustomerDto customerDto = TransferModelToDto.transferToCustomerDto(customer);

        // Assert
        assertEquals(customerDto.getUsername(), customer.getUsername());
    }

    @Test
    void checksIfShoppingCartChangesToShoppingCartDto() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setTotalPrice(50);

        ShoppingCartDto shoppingCartDto = TransferModelToDto.transferToShoppingCartDto(shoppingCart);

        assertEquals(shoppingCartDto.getId(), shoppingCart.getId());
        assertEquals(shoppingCartDto.getTotalPrice(), shoppingCart.getTotalPrice());
    }

    @Test
    void checksIfProductListInShoppingCartDtoTransfersInProductListDtoInShoppingCart() {
        // Arrange
        ShoppingCart shoppingCart = new ShoppingCart();
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        Product product2 = new Product();

        product.setProductName("product");
        product.setProductName("product2");

        productList.add(product);
        productList.add(product);
        shoppingCart.setProductList(productList);

        // Act
        ShoppingCartDto shoppingCartDto = TransferModelToDto.transferToShoppingCartDto(shoppingCart);

        // Assert
        assertEquals(shoppingCartDto.getProductList().size(), shoppingCart.getProductList().size());
    }

    @Test
    void testIfReturnCartDtoTransfersToReturnCart() {
        ReturnCart returnCart = new ReturnCart();
        returnCart.setId(1L);
        returnCart.setProcessed(true);
        returnCart.setTotalPrice(100);
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        Product product2 = new Product();

        product.setProductName("product");
        product2.setProductName("product2");

        productList.add(product);
        productList.add(product2);
        returnCart.setReturnProductList(productList);

        // Act
        ReturnCartDto returnCartDto = TransferModelToDto.transferToReturnCartDto(returnCart);

        // Assert
        assertEquals(returnCartDto.getId(), 1L);
        assertEquals(returnCartDto.getTotalPrice(), 100);
        assertTrue(returnCartDto.isProcessed());
        assertEquals(returnCartDto.getReturnProductList().size(), returnCart.getReturnProductList().size());
    }
}
