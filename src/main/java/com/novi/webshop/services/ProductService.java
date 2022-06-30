package com.novi.webshop.services;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.model.Attachment;
import com.novi.webshop.model.Product;

import java.net.URI;
import java.net.URL;
import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();

    ProductDto getProductByName(String productName);

    ProductDto uploadPicture(Long productId, String downloadUrl);

    List<ProductDto> getProductsByCategory(String categoryName);

    List<ProductDto> getProductsPriceRange(int minimumPrice, int maximumPrice);

    ProductDto createProduct(ProductDto productDto);

    ProductDto changeProduct(Long id, String type, ProductDto productDto);

    void deleteProduct(Long id);

}
