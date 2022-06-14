package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.model.Product;
import com.novi.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> allProductList = new ArrayList<>(productRepository.findAll());
        List<ProductDto> allProductDtoList = new ArrayList<>();
        for (Product allProduct : allProductList) {
            allProductDtoList.add(transferToProductDto(allProduct));
        }
        return allProductDtoList;
    }

    public ProductDto getProductByName(String productName) {
        List<Product> allProductList = new ArrayList<>(productRepository.findAll());
        for (Product product : allProductList) {
            if (product.getProduct().equalsIgnoreCase(productName)) {
                return transferToProductDto(product);
            }
        }
        // Doesn't return a RecordNotFoundException because this method is nested in the createProduct method!
        return null;
    }

    public List<ProductDto> getProductsByCategory(String categoryName) {
        List<Product> allProductList = new ArrayList<>(productRepository.findAll());
        List<ProductDto> allProductDtoList = new ArrayList<>();
        for (int i = 0; i < allProductList.size(); i++) {
            if(allProductList.get(i).getCategory().equalsIgnoreCase(categoryName)) {
                allProductDtoList.add(transferToProductDto(allProductList.get(i)));
            }
        }
        if(allProductDtoList.size() > 0) {
            return allProductDtoList;
        }   else {
            throw new RecordNotFoundException("No items in this category found");
        }
    }

    public List<ProductDto> getProductsPriceRange(int minimumPrice, int maximumPrice) {
        List<Product> allProductList = new ArrayList<>(productRepository.findAll());
        List<ProductDto> allProductDtoList = new ArrayList<>();
        for (int i = 0; i < allProductList.size(); i++) {
            if (allProductList.get(i).getSellingPrice() > minimumPrice && allProductList.get(i).getSellingPrice() < maximumPrice) {
                allProductDtoList.add(transferToProductDto(allProductList.get(i)));
            }
        }
        if (allProductDtoList.size() > 0) {
            return allProductDtoList;
        } else {
            throw new RecordNotFoundException("No products were found in this price category");
        }
    }

    public ProductDto createProduct(ProductDto productDto) {
        if (getProductByName(productDto.getProduct()) != null) {
            throw new RecordNotFoundException("Product already exists");
        } else {
            Product product = transferToProduct(productDto);
            final Product savedProduct = productRepository.save(product);

            return transferToProductDto(savedProduct);
        }
    }

    public ProductDto changeProduct(Long id, String type, ProductDto productDto) {
        if (productRepository.findById(id).isPresent()) {
            Product chosenProduct = productRepository.findById(id).get();
            if(type.equalsIgnoreCase("product")) {
                chosenProduct.setProduct(productDto.getProduct());
                productRepository.save(chosenProduct);
            } else if (type.equalsIgnoreCase("category")) {
                chosenProduct.setCategory(productDto.getCategory());
                productRepository.save(chosenProduct);
            } else if (type.equalsIgnoreCase("sellingPrice")) {
                chosenProduct.setSellingPrice(productDto.getSellingPrice());
               productRepository.save(chosenProduct);
            } else if (type.equalsIgnoreCase("buyingPrice")) {
                chosenProduct.setRetailPrice(productDto.getRetailPrice());
               productRepository.save(chosenProduct);
            } else {
                throw new RecordNotFoundException("Type to change not found");
            }
        } else {
            throw new RecordNotFoundException("Object not found");
        }
        return productDto;
    }

    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("You cannot delete a product that does not exists");
        }
    }

    protected Product transferToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProduct(productDto.getProduct());
        product.setCategory(productDto.getCategory());
        product.setSellingPrice(productDto.getSellingPrice());
        product.setRetailPrice(productDto.getRetailPrice());
        return product;
    }

    protected ProductDto transferToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProduct(product.getProduct());
        productDto.setCategory(product.getCategory());
        productDto.setSellingPrice(product.getSellingPrice());
        productDto.setRetailPrice(product.getRetailPrice());
        return productDto;
    }
}
