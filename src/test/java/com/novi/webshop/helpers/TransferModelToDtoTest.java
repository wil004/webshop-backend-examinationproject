package com.novi.webshop.helpers;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.ReturnsDto;
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
        List<QuantityAndProduct> productList = new ArrayList<>();
        QuantityAndProduct product = new QuantityAndProduct();
        Product product2 = new Product();

        product.setProduct(product2);


        productList.add(product);
        productList.add(product);
        shoppingCart.setQuantityAndProductList(productList);

        // Act
        ShoppingCartDto shoppingCartDto = TransferModelToDto.transferToShoppingCartDto(shoppingCart);

        // Assert
        assertEquals(shoppingCartDto.getProductList().size(), shoppingCart.getQuantityAndProductList().size());
    }


}
