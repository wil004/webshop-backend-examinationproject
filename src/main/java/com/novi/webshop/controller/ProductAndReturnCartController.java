package com.novi.webshop.controller;

import com.novi.webshop.dto.ProductAndReturnCartDto;
import com.novi.webshop.services.ProductAndReturnCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("product-returncart")
public class ProductAndReturnCartController {
    private final ProductAndReturnCartService productAndReturnCartService;

    @Autowired
    public ProductAndReturnCartController(ProductAndReturnCartService productAndReturnCartService) {
        this.productAndReturnCartService = productAndReturnCartService;
    }


    @PostMapping(path = "{productId}/{returnCartId}" , consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductAndReturnCartDto> connectProductWithReturnCart(@PathVariable Long productId, @PathVariable Long returnCartId, @RequestBody ProductAndReturnCartDto productAndReturnCartDto) {
        final URI location = URI.create("/product-returncart" + productAndReturnCartDto.getProductId() + productAndReturnCartDto.getReturnCartId());
        return ResponseEntity.created(location).body(productAndReturnCartService.connectProductWithReturnCart(productId, returnCartId, productAndReturnCartDto));
    }
}
