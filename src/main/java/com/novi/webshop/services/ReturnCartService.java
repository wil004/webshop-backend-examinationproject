package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ReturnCartDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.model.Product;
import com.novi.webshop.model.ReturnCart;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.ProductRepository;
import com.novi.webshop.repository.ReturnCartRepository;
import com.novi.webshop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnCartService {

    private final ReturnCartRepository returnCartRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ReturnCartService(ReturnCartRepository returnCartRepository, ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository, ShoppingCartService shoppingCartService) {
        this.returnCartRepository = returnCartRepository;
        this.productRepository = productRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartService = shoppingCartService;
    }


    public List<ReturnCartDto> getAllReturnCarts() {
        List<ReturnCart> returnCartList = returnCartRepository.findAll();
        List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
        for (int i = 0; i < returnCartList.size(); i++) {
            returnCartDtoList.add(transferToReturnCartDto(returnCartList.get(i)));
        }
        return returnCartDtoList;
    }

    public List<ReturnCartDto> getProcessedOrNotProcessedReturnCarts(boolean processedOrNotProcessed) {
        List<ReturnCart> allReturnCartList = returnCartRepository.findAll();
        List<ReturnCart> allReturnCartsWithProcessedStatus = new ArrayList<>();
            for (int i = 0; i < allReturnCartList.size(); i++) {
                if (processedOrNotProcessed) {
                    if (allReturnCartList.get(i).isProcessed()) {
                        allReturnCartsWithProcessedStatus.add(allReturnCartList.get(i));
                    }
                } else {
                    if (!allReturnCartList.get(i).isProcessed()) {
                        allReturnCartsWithProcessedStatus.add(allReturnCartList.get(i));
                    }
                }
            }

            List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
            for (int i = 0; i < allReturnCartsWithProcessedStatus.size(); i++) {
                returnCartDtoList.add(transferToReturnCartDto(allReturnCartsWithProcessedStatus.get(i)));
            }

            return returnCartDtoList;
        }

    public List<ReturnCartDto> getReturnCartByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber) {
        List<ShoppingCartDto> shoppingCartDtoList = shoppingCartService.getShoppingCartsByNameAndAddress(firstName, lastName, zipcode, houseNumber);
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
        for (int i = 0; i < shoppingCartDtoList.size(); i++) {
            if(shoppingCartRepository.findById(shoppingCartDtoList.get(i).getId()).isPresent()) {
                shoppingCartList.add(shoppingCartRepository.findById(shoppingCartDtoList.get(i).getId()).orElseThrow());
            }
            for (int j = 0; j < shoppingCartList.get(i).getReturnCartList().size(); j++) {
                returnCartDtoList.add(transferToReturnCartDto(shoppingCartList.get(i).getReturnCartList().get(j)));
            }
        }
        return returnCartDtoList;
    }

    public List<ReturnCartDto> getReturnCartByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber, String additionalHouseNumber) {
        List<ShoppingCartDto> shoppingCartDtoList = shoppingCartService.getShoppingCartsByNameAndAddress(firstName, lastName, zipcode, houseNumber, additionalHouseNumber);
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
        for (int i = 0; i < shoppingCartDtoList.size(); i++) {
            if(shoppingCartRepository.findById(shoppingCartDtoList.get(i).getId()).isPresent()) {
                shoppingCartList.add(shoppingCartRepository.findById(shoppingCartDtoList.get(i).getId()).orElseThrow());
            }
            for (int j = 0; j < shoppingCartList.get(i).getReturnCartList().size(); j++) {
                returnCartDtoList.add(transferToReturnCartDto(shoppingCartList.get(i).getReturnCartList().get(j)));
            }
        }
        return returnCartDtoList;
    }

    public ReturnCartDto changeProcessedStatus(Long id, boolean processed) {
        if(returnCartRepository.findById(id).isPresent()) {
            ReturnCart returnCart = returnCartRepository.findById(id).orElseThrow();
            returnCart.setProcessed(processed);
            ReturnCart savedReturnCart = returnCartRepository.save(returnCart);
            return transferToReturnCartDto(savedReturnCart);
        }
        else {
            throw new RecordNotFoundException("Couldn't find return-cart");
        }
    }

    public ReturnCartDto createReturnProducts(Long shoppingCartId) {
        ReturnCart returnCart = new ReturnCart();
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow();
        if (shoppingCartRepository.findById(shoppingCartId).isPresent()) {
            returnCart.setShoppingCart(shoppingCart);
            if (within30DaysReturnTime(returnCart.getShoppingCart())) {
                ReturnCart savedReturnCart = returnCartRepository.save(returnCart);
                return transferToReturnCartDto(savedReturnCart);
            } else {
                throw new RecordNotFoundException("Returning time has been expired, you can't return products anymore!");
            }
        } else {
            throw new RecordNotFoundException("Couldn't connect a shopping cart to the return cart");
        }
    }

    private boolean within30DaysReturnTime(ShoppingCart shoppingCart) {
        long maximumReturnTime = shoppingCart.getOrderDateInMilliSeconds() + 1000L * 60 * 60 * 24 * 30;
        long currentTime = System.currentTimeMillis();
        if (currentTime > maximumReturnTime) {
            return false;
        } else {
            return true;
        }
    }

    public ReturnCartDto connectProductWithReturnCart(Long returnCartId, Long productId, ProductDto productDto) {
        ReturnCart returnCart = returnCartRepository.findById(returnCartId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        ShoppingCart shoppingCart = returnCart.getShoppingCart();

        if (returnCartRepository.findById(returnCartId).isPresent() && productRepository.findById(productId).isPresent() &&
                shoppingCartRepository.findById(returnCart.getShoppingCart().getId()).isPresent()) {
            for (int i = 0; i < shoppingCart.getProductList().size(); i++) {
                if (shoppingCart.getProductList().get(i) == product) {
                    if (productDto.getAmountOfProducts() <= shoppingCart.getProductList().get(i).getAmountOfProducts()) {
                        List<ReturnCart> returnCartsInProduct = product.getReturnCartList();
                        returnCartsInProduct.add(returnCart);
                        product.setReturnCartList(returnCartsInProduct);
                        productRepository.save(product);

                        List<Product> productList = returnCart.getReturnProductList();
                        product.setAmountOfReturningProducts(productDto.getAmountOfReturningProducts());
                        for (int j = 0; j < productList.size(); j++) {
                            if(product.getProduct().equalsIgnoreCase(productList.get(i).getProduct())) {
                                throw new RecordNotFoundException("Product is already in return cart!");
                            }
                        }
                        productList.add(product);
                        product.setAmountOfReturningProducts(productDto.getAmountOfReturningProducts());
                        returnCart.setReturnProductList(productList);
                        returnCart.setTotalPrice(returnCart.getTotalPrice() + product.getSellingPrice() * productDto.getAmountOfReturningProducts());

                        shoppingCart.getProductList().get(i).setAmountOfProducts(shoppingCart.getProductList().get(i).getAmountOfProducts() - productDto.getAmountOfReturningProducts());
                        shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() - product.getSellingPrice() * productDto.getAmountOfReturningProducts());

                        shoppingCartRepository.save(shoppingCart);
                        returnCartRepository.save(returnCart);
                        return transferToReturnCartDto(returnCart);
                    } else {
                        throw new RecordNotFoundException("You cannot return more products then you ordered");
                    }
                    }

            }
            throw new RecordNotFoundException("Couldn't find the product you want to return");


        }
        throw new RecordNotFoundException("Couldn't find return cart");
    }

    private ReturnCartDto transferToReturnCartDto(ReturnCart returnCart) {
        ReturnCartDto returnCartDto = new ReturnCartDto();
        returnCartDto.setId(returnCart.getId());
        returnCartDto.setTotalPrice(returnCart.getTotalPrice());
        returnCartDto.setProcessed(returnCart.isProcessed());
        returnCartDto.setShoppingCartDto(shoppingCartService.transferToShoppingCartDto(returnCart.getShoppingCart()));
        if(returnCart.getReturnProductList() != null) {
            List<ProductDto> returnCartDtoProductList = new ArrayList<>();
            for (int i = 0; i < returnCart.getReturnProductList().size(); i++) {
                returnCartDtoProductList.add(transferToProductDto(returnCart.getReturnProductList().get(i)));
            }
            returnCartDto.setReturnProductList(returnCartDtoProductList);
        }
        return returnCartDto;
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
        productDto.setAmountOfReturningProducts(product.getAmountOfReturningProducts());
        return productDto;
    }

}
