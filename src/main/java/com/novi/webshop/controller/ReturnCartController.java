package com.novi.webshop.controller;

import com.novi.webshop.dto.ReturnCartDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.dto.ShoppingCartInputDto;
import com.novi.webshop.services.ReturnCartService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("returncart")
public class ReturnCartController {
    private final ReturnCartService returnCartService;

    public ReturnCartController(ReturnCartService returnCartService) {
        this.returnCartService = returnCartService;
    }

    @PostMapping(path = "/{shoppingCartId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReturnCartDto> createReturnCart(@PathVariable Long shoppingCartId) {
        final URI location = URI.create("/returncart" + shoppingCartId);
        return ResponseEntity.created(location).body(returnCartService.createReturnProducts(shoppingCartId));
    }
}