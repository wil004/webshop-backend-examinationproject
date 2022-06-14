package com.novi.webshop.controller;

import com.novi.webshop.dto.ProductAndShoppingCartDto;
import com.novi.webshop.model.ProductAndShoppingCart;
import com.novi.webshop.repository.ProductAndShoppingCartRepository;
import com.novi.webshop.services.ProductAndShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("product-shoppingcart")
public class ProductAndShoppingCartController {

    private ProductAndShoppingCartService productAndShoppingCartService;

    @Autowired
    public ProductAndShoppingCartController(ProductAndShoppingCartService productAndShoppingCartService) {
        this.productAndShoppingCartService = productAndShoppingCartService;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductAndShoppingCartDto> connectTelevisionsAndWallBracketsInModel(@RequestBody ProductAndShoppingCartDto productAndShoppingCartDto) {
        final URI location = URI.create("/product-shoppingcart" + productAndShoppingCartDto.getProductId() + productAndShoppingCartDto.getShoppingCartId());
        return ResponseEntity.created(location).body(productAndShoppingCartService.connectProductWithShoppingCart(productAndShoppingCartDto));
    }

}
