package com.novi.webshop.services;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.helpers.TransferDtoToModel;
import com.novi.webshop.model.Product;
import com.novi.webshop.repository.ProductRepository;
import org.h2.value.Transfer;
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
        product.setSellingPrice(150);
        product1.setSellingPrice(50);
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
        product.setRetailPrice(100);
        product2.setProductName("changedToThisName");
        product2.setRetailPrice(200);
        productService.createProduct(product);

        // Act
        ProductDto changedProduct = productService.changeProduct(1L, "product", product2);
        ProductDto changeProduct2 = productService.changeProduct(1L, "retailPrice", product2);

        // Assert
        assertEquals(changedProduct.getProductName(),"changedToThisName");
        assertEquals(changedProduct.getRetailPrice(), 200);
    }

    @Test
    void deleteProduct() {
    }
}