package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ReturnCartDto;
import com.novi.webshop.model.ReturnCart;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.ReturnCartRepository;
import com.novi.webshop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReturnCartService {

    private final ReturnCartRepository returnCartRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ReturnCartService(ReturnCartRepository returnCartRepository, ShoppingCartRepository shoppingCartRepository) {
        this.returnCartRepository = returnCartRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }


    public ReturnCartDto createReturnProducts(Long shoppingCartId) {
        ReturnCart returnCart = new ReturnCart();
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow();
        if (shoppingCartRepository.findById(shoppingCartId).isPresent()) {
            returnCart.setShoppingCart(shoppingCart);
            if (within30DaysReturnTime(returnCart.getShoppingCart())) {
                ReturnCart savedReturnCart = returnCartRepository.save(returnCart);
                return transferToReturnProductsDto(savedReturnCart);
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

    private ReturnCartDto transferToReturnProductsDto(ReturnCart returnCart) {
        ReturnCartDto returnCartDto = new ReturnCartDto();
        returnCartDto.setId(returnCart.getId());
        returnCartDto.setShoppingCartId(returnCart.getShoppingCart().getId());
        return returnCartDto;
    }
}
