package com.novi.webshop.services;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;
    private ProductRepository productRepository;


    // TESTS WORK IF YOU TEST THEM 1 BY 1

    @Test
    void testsIfAProductIsCreated() {
        // Arrange
        ProductDto product = new ProductDto();
        product.setProductName("test");

        // Act
        productService.createProduct(product);
        ProductDto productName = productService.getProductByName("test");

        // Assert
        assertEquals(product.getProductName(), productName.getProductName());
    }

    @Test
    void testsIfYouGetAllProducts() {
        //Arrange
        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto product = new ProductDto();
        ProductDto product1 = new ProductDto();
        product.setProductName("test");
        product1.setProductName("test2");
        productDtoList.add(product);
        productDtoList.add(product1);
        productService.createProduct(product);
        productService.createProduct(product1);


        // Act
        List<ProductDto> productDtoTestResultList = productService.getAllProducts();

        // Assert
        assertEquals(productDtoList.get(0).getProductName(), productDtoTestResultList.get(0).getProductName());

    }


    @Test
    void testGetProductByName() {
        // Arrange
        ProductDto product = new ProductDto();
        product.setProductName("test");
        productService.createProduct(product);

        // Act
        ProductDto productDtoToTest = productService.getProductByName("test");

        // Assert
        assertEquals(product.getProductName(), productDtoToTest.getProductName());
    }

    @Test
    void getProductsByCategory() {
        // Arrange
        ProductDto product = new ProductDto();
        product.setCategory("test");
        productService.createProduct(product);

        // Act
        List<ProductDto> productDtoToTest = productService.getProductsByCategory("test");

        // Assert
        assertEquals(productDtoToTest.get(0).getCategory(), product.getCategory());
    }

    @Test
    void getProductsPriceRange() {
        // Arrange
        ProductDto product = new ProductDto();
        ProductDto product1 = new ProductDto();
        product.setProductName("test");
        product.setProductName("test1");
        product.setPrice(150);
        product1.setPrice(50);
        productService.createProduct(product);
        productService.createProduct(product1);

        // Act
        List<ProductDto> productDtoToTest = productService.getProductsPriceRange(100, 300);
        // Assert
        assertEquals(productDtoToTest.size(), 1);
    }

    @Test
    void testIfAttributesInProductChanges() {
        // Arrange
        ProductDto product = new ProductDto();
        ProductDto product2 = new ProductDto();
        product.setProductName("test");
        product2.setProductName("changedToThisName");
        product.setPrice(100);
        product2.setPrice(200);
        productService.createProduct(product);

        // Act
        ProductDto changedProduct = productService.changeProduct(1L, "productName", product2);
        ProductDto changeProduct2 = productService.changeProduct(1L, "price", product2);

        // Assert
        assertEquals(changedProduct.getProductName(),"changedToThisName");
        assertEquals(changeProduct2.getPrice(), 200);
    }

    @Test
    void deleteProduct() {
    }
}