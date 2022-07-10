package com.novi.webshop.services;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDto connectProductWithShoppingCart(Long shoppingCartId, Long productId, ProductDto productDto);

    ShoppingCartDto createShoppingCard(Long customerId);
}
