package com.novi.webshop.controller;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ReturnCartDto;
import com.novi.webshop.services.ReturnCartServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("returncart")
public class ReturnCartController {
    private final ReturnCartServiceImpl returnCartServiceImpl;

    public ReturnCartController(ReturnCartServiceImpl returnCartServiceImpl) {
        this.returnCartServiceImpl = returnCartServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ReturnCartDto>> getAllReturnCarts() {
        return ResponseEntity.ok(returnCartServiceImpl.getAllReturnCarts());
    }

    @GetMapping("/processed-status={processed}")
    public ResponseEntity<List<ReturnCartDto>> getAllNotProcessedReturnCarts(@PathVariable boolean processed) {
        return ResponseEntity.ok(returnCartServiceImpl.getProcessedOrNotProcessedReturnCarts(processed));
    }

    @GetMapping("/{firstName}/{lastName}/{zipcode}/{houseNumber}")
    public ResponseEntity<List<ReturnCartDto>> getReturnCartsByNameAndAddress(@PathVariable String firstName, @PathVariable String lastName,
                                                                                  @PathVariable String zipcode, @PathVariable int houseNumber) {
        return ResponseEntity.ok(returnCartServiceImpl.getReturnCartByNameAndAddress(firstName, lastName, zipcode, houseNumber));
    }

    @GetMapping("/{firstName}/{lastName}/{zipcode}/{houseNumber}/{additionalHouseNumber}")
    public ResponseEntity<List<ReturnCartDto>> getReturnCartsByNameAndAddress2(@PathVariable String firstName, @PathVariable String lastName,
                                                                                   @PathVariable String zipcode, @PathVariable int houseNumber,
                                                                                   @PathVariable String additionalHouseNumber) {
        return ResponseEntity.ok(returnCartServiceImpl.getReturnCartByNameAndAddress(firstName, lastName, zipcode, houseNumber, additionalHouseNumber));
    }

    @PutMapping(value = "change-processed-status={processed}/id={id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReturnCartDto> changeProcessedStatus(@PathVariable Long id, @PathVariable boolean processed) {
        return ResponseEntity.ok(returnCartServiceImpl.changeProcessedStatus(id, processed));
    }

    @PutMapping(value = "id={returnCartId}/productid={productId}",
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ReturnCartDto> addProductToReturnCart(@PathVariable Long returnCartId , @PathVariable Long productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(returnCartServiceImpl.connectProductWithReturnCart(returnCartId, productId, productDto));
    }

    @PostMapping(path = "/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReturnCartDto> createReturnCart(@PathVariable Long orderId) {
        final URI location = URI.create("/returncart" + orderId);
        return ResponseEntity.created(location).body(returnCartServiceImpl.createReturnProducts(orderId));
    }
}