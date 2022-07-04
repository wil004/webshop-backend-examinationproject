package com.novi.webshop.helpers;

import com.novi.webshop.dto.CustomerDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ReturnsDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Returns;
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
        assertEquals(shoppingCart.getQuantityAndProductList().size(), shoppingCartDto.getProductList().size());
    }

    @Test
    void testIfReturnCartDtoTransfersToReturnCart() {
        ReturnsDto returnsDto = new ReturnsDto();
        returnsDto.setId(1L);
         returnsDto.setProcessed(true);
         returnsDto.setTotalPrice(100);
        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto = new ProductDto();
        ProductDto productDto2 = new ProductDto();

        productDto.setProductName("product");
        productDto2.setProductName("product2");

        productDtoList.add(productDto);
        productDtoList.add(productDto2);
        returnsDto.setReturnProductList(productDtoList);

        // Act
        Returns aReturns = TransferDtoToModel.transferToReturnCart(returnsDto);

        // Assert
        assertEquals(aReturns.getId(), 1L);
        assertEquals(aReturns.getTotalPrice(), 100);
        assertTrue(aReturns.isProcessed());
        assertEquals(aReturns.getQuantityAndProductList().size(), returnsDto.getReturnProductList().size());
    }
}
