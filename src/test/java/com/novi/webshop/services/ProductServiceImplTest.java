package com.novi.webshop.services;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;



    @BeforeAll
    void setup() {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("test");
        productDto.setPrice(200);
        productDto.setCategory("test");
        ProductDto productDto2 = new ProductDto();
        productDto2.setProductName("changeThisName");
        productDto2.setPrice(200);
        productDto2.setCategory("test");
        productService.createProduct(productDto);
        productService.createProduct(productDto2);
    }

    @Test
    void testsIfAProductIsCreated() {
        // Arrange
        ProductDto product = new ProductDto();
        product.setProductName("test");

        // Act
        ProductDto productName = productService.getProductByName("test");

        // Assert
        assertEquals(product.getProductName(), productName.getProductName());
    }

    @Test
    void testsIfYouGetAllProducts() {
        List<ProductDto> productDtoTestResultList = productService.getAllProducts();

        assertEquals(2, productDtoTestResultList.size());

    }


    @Test
    void testGetProductByName() {
        // Arrange
        ProductDto product = new ProductDto();
        product.setProductName("test");

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

        // Act
        List<ProductDto> productDtoToTest = productService.getProductsByCategory("test");

        // Assert
        assertEquals(product.getCategory(), productDtoToTest.get(0).getCategory());
    }


    @Test
    void getProductsPriceRange() {
        List<ProductDto> productDtoToTest = productService.getProductsPriceRange(100, 300);
        assertEquals(2, productDtoToTest.size());
    }

    @Test
    void changeProductAttributes() {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("changed Name");
        productDto.setPrice(201);
        productDto.setCategory("changed Category");
        ProductDto product = productService.getAllProducts().get(1);

        ProductDto changedProduct = productService.changeProduct(product.getId(), "productName", productDto);
        ProductDto changedProduct2 = productService.changeProduct(product.getId(), "category", productDto);
        ProductDto changedProduct3 = productService.changeProduct(product.getId(), "price", productDto);

        assertEquals(productDto.getProductName(), changedProduct.getProductName());
        assertEquals(productDto.getCategory(), changedProduct2.getCategory());
        assertEquals(productDto.getPrice(), changedProduct3.getPrice());
    }
}