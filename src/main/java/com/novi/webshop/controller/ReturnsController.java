package com.novi.webshop.controller;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ReturnsDto;
import com.novi.webshop.services.ReturnsServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("returns")
public class ReturnsController {
    private final ReturnsServiceImpl returnCartServiceImpl;

    public ReturnsController(ReturnsServiceImpl returnCartServiceImpl) {
        this.returnCartServiceImpl = returnCartServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ReturnsDto>> getAllReturnCarts() {
        return ResponseEntity.ok(returnCartServiceImpl.getAllReturns());
    }

    @GetMapping("/processed-status={processed}")
    public ResponseEntity<List<ReturnsDto>> getAllNotProcessedReturnCarts(@PathVariable boolean processed) {
        return ResponseEntity.ok(returnCartServiceImpl.getProcessedOrNotProcessedReturns(processed));
    }

    @GetMapping("/{firstName}/{lastName}/{zipcode}/{houseNumber}")
    public ResponseEntity<List<ReturnsDto>> getReturnCartsByNameAndAddress(@PathVariable String firstName, @PathVariable String lastName,
                                                                           @PathVariable String zipcode, @PathVariable int houseNumber) {
        return ResponseEntity.ok(returnCartServiceImpl.getReturnByNameAndAddress(firstName, lastName, zipcode, houseNumber));
    }

    @GetMapping("/{firstName}/{lastName}/{zipcode}/{houseNumber}/{additionalHouseNumber}")
    public ResponseEntity<List<ReturnsDto>> getReturnCartsByNameAndAddress2(@PathVariable String firstName, @PathVariable String lastName,
                                                                            @PathVariable String zipcode, @PathVariable int houseNumber,
                                                                            @PathVariable String additionalHouseNumber) {
        return ResponseEntity.ok(returnCartServiceImpl.getReturnByNameAndAddress(firstName, lastName, zipcode, houseNumber, additionalHouseNumber));
    }

    @PutMapping(value = "/change-processed-status={processed}/id={id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> changeProcessedStatus(@PathVariable Long id, @PathVariable boolean processed) {
        return ResponseEntity.ok(returnCartServiceImpl.changeProcessedStatus(id, processed));
    }

    @PutMapping(value = "/id={returnsId}/productid={productId}",
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ReturnsDto> addProductToReturnCart(@PathVariable Long returnsId , @PathVariable Long productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(returnCartServiceImpl.connectProductWithReturn(returnsId, productId, productDto));
    }

    @PostMapping(path = "/{orderId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReturnsDto> createReturnCart(@PathVariable Long orderId, @RequestBody ReturnsDto returnsDto) {
        final URI location = URI.create("/returncart" + orderId);
        return ResponseEntity.created(location).body(returnCartServiceImpl.createReturnProducts(orderId, returnsDto));
    }
}