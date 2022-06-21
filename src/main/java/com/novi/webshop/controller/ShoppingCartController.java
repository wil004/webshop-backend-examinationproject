package com.novi.webshop.controller;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.services.ShoppingCartService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("shoppingcart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PutMapping(value = "id={shoppingCartId}/productid={productId}",
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ShoppingCartDto> addProductToShoppingCart(@PathVariable Long shoppingCartId ,@PathVariable Long productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(shoppingCartService.connectProductWithShoppingCart(shoppingCartId, productId, productDto));
    }

    @DeleteMapping("/{id}")
    public void deleteShoppingCart(@PathVariable long id) {
        shoppingCartService.deleteShoppingCart(id);
    }
}
