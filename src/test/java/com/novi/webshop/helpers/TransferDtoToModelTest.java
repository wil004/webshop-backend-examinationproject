package com.novi.webshop.helpers;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ReturnCartDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.ReturnCart;
import com.novi.webshop.model.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class TransferDtoToModelTest {

    @Test
    void checksIfCustomerDtoChangesToCustomer() {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUsername("test");

        // Act
        Customer customer = TransferDtoToModel.transferToCustomer(customerDto);

        // Assert
        assertEquals(customer.getUsername(), customerDto.getUsername());
    }

    @Test
    void checksIfShoppingCartDtoChangesToShoppingCart() {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(1L);
        shoppingCartDto.setTotalPrice(50);

        ShoppingCart shoppingCart = TransferDtoToModel.transferToShoppingCart(shoppingCartDto);

        assertEquals(shoppingCart.getId(), shoppingCartDto.getId());
        assertEquals(shoppingCart.getTotalPrice(), shoppingCartDto.getTotalPrice());
    }

    @Test
    void checksIfProductListDtoInShoppingCartDtoTransfersInProductListInShoppingCart() {
        // Arrange
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto = new ProductDto();
        ProductDto productDto2 = new ProductDto();

        productDto.setProductName("product");
        productDto2.setProductName("product2");

        productDtoList.add(productDto);
        productDtoList.add(productDto2);
        shoppingCartDto.setProductList(productDtoList);

        // Act
        ShoppingCart shoppingCart = TransferDtoToModel.transferToShoppingCart(shoppingCartDto);

        // Assert
        assertEquals(shoppingCart.getProductList().size(), shoppingCartDto.getProductList().size());
    }

    @Test
    void testIfReturnCartDtoTransfersToReturnCart() {
        ReturnCartDto returnCartDto = new ReturnCartDto();
        returnCartDto.setId(1L);
         returnCartDto.setProcessed(true);
         returnCartDto.setTotalPrice(100);
        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto = new ProductDto();
        ProductDto productDto2 = new ProductDto();

        productDto.setProductName("product");
        productDto2.setProductName("product2");

        productDtoList.add(productDto);
        productDtoList.add(productDto2);
        returnCartDto.setReturnProductList(productDtoList);

        // Act
        ReturnCart returnCart = TransferDtoToModel.transferToReturnCart(returnCartDto);

        // Assert
        assertEquals(returnCart.getId(), 1L);
        assertEquals(returnCart.getTotalPrice(), 100);
        assertTrue(returnCart.isProcessed());
        assertEquals(returnCart.getReturnProductList().size(), returnCartDto.getReturnProductList().size());
    }




}
