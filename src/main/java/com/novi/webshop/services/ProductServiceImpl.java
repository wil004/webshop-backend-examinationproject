package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.helpers.TransferDtoToModel;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.Product;
import com.novi.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> allProductList = new ArrayList<>(productRepository.findAll());
        List<ProductDto> allProductDtoList = new ArrayList<>();
        for (Product allProduct : allProductList) {
            allProductDtoList.add(TransferModelToDto.transferToProductDto(allProduct));
        }
        return allProductDtoList;
    }

    @Override
    public ProductDto getProductByName(String productName) {
        List<Product> allProductList = new ArrayList<>(productRepository.findAll());
        for (Product product : allProductList) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                return TransferModelToDto.transferToProductDto(product);
            }
        }
        // Doesn't return a RecordNotFoundException because this method is nested in the createProduct method!
        return null;
    }

    @Override
    public ProductDto uploadPicture(Long productId, String downloadUrl) {
        Product product = productRepository.findById(productId).orElseThrow();
        if(productRepository.findById(productId).isPresent()) {
            product.setProductPictureUrl(downloadUrl);
            Product savedProduct = productRepository.save(product);
            return TransferModelToDto.transferToProductDto(savedProduct);
        } else {
            throw new RecordNotFoundException("Couldn't find product");
        }
    }

    @Override
    public List<ProductDto> getProductsByCategory(String categoryName) {
        List<Product> allProductList = new ArrayList<>(productRepository.findAll());
        List<ProductDto> allProductDtoList = new ArrayList<>();
        for (int i = 0; i < allProductList.size(); i++) {
            if(allProductList.get(i).getCategory().equalsIgnoreCase(categoryName)) {
                allProductDtoList.add(TransferModelToDto.transferToProductDto(allProductList.get(i)));
            }
        }
        if(allProductDtoList.size() > 0) {
            return allProductDtoList;
        }   else {
            throw new RecordNotFoundException("No items in this category found");
        }
    }

    @Override
    public List<ProductDto> getProductsPriceRange(int minimumPrice, int maximumPrice) {
        List<Product> allProductList = new ArrayList<>(productRepository.findAll());
        List<ProductDto> allProductDtoList = new ArrayList<>();
        for (int i = 0; i < allProductList.size(); i++) {
            if (allProductList.get(i).getPrice() > minimumPrice && allProductList.get(i).getPrice() < maximumPrice) {
                allProductDtoList.add(TransferModelToDto.transferToProductDto(allProductList.get(i)));
            }
        }
        if (allProductDtoList.size() > 0) {
            return allProductDtoList;
        } else {
            throw new RecordNotFoundException("No products were found in this price category");
        }
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        if (getProductByName(productDto.getProductName()) != null) {
            throw new RecordNotFoundException("Product already exists");
        } else {
            Product product = TransferDtoToModel.transferToProduct(productDto);
            final Product savedProduct = productRepository.save(product);

            return TransferModelToDto.transferToProductDto(savedProduct);
        }
    }

    @Override
    public ProductDto changeProduct(Long id, String type, ProductDto productDto) {
        if (productRepository.findById(id).isPresent()) {
            Product chosenProduct = productRepository.findById(id).get();
            if(type.equalsIgnoreCase("productName")) {
                chosenProduct.setProductName(productDto.getProductName());
                productRepository.save(chosenProduct);
            } else if (type.equalsIgnoreCase("category")) {
                chosenProduct.setCategory(productDto.getCategory());
                productRepository.save(chosenProduct);
            } else if (type.equalsIgnoreCase("price")) {
                chosenProduct.setPrice(productDto.getPrice());
               productRepository.save(chosenProduct);
            } else {
                throw new RecordNotFoundException("Type to change not found");
            }
        } else {
            throw new RecordNotFoundException("Object not found");
        }
        return productDto;
    }

    @Override
    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("You cannot delete a product that does not exists");
        }
    }

}
