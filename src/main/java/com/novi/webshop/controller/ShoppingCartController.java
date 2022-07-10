package com.novi.webshop.controller;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.services.ShoppingCartServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("shoppingcart")
public class ShoppingCartController {

    private final ShoppingCartServiceImpl shoppingCartServiceImpl;

    public ShoppingCartController(ShoppingCartServiceImpl shoppingCartServiceImpl) {
        this.shoppingCartServiceImpl = shoppingCartServiceImpl;
    }

    @PutMapping(value = "id={customerId}/productid={productId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ShoppingCartDto> addProductToShoppingCart(@PathVariable Long customerId, @PathVariable Long productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(shoppingCartServiceImpl.connectProductWithShoppingCart(customerId, productId, productDto));
    }
}
