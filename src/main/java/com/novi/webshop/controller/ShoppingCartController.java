package com.novi.webshop.controller;

import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.dto.ShoppingCartInputDto;
import com.novi.webshop.services.ShoppingCartService;
import org.apache.coyote.Response;
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

    @GetMapping
    public ResponseEntity<List<ShoppingCartDto>> getAllShoppingCarts() {
        return ResponseEntity.ok(shoppingCartService.getAllShoppingCarts());
    }

    @GetMapping("/notprocessed")
    public ResponseEntity<List<ShoppingCartDto>> getAllNotProcessedShoppingCarts() {
        return ResponseEntity.ok(shoppingCartService.getStillToProcessShoppingCarts());
    }

    @GetMapping("/{firstName}/{lastName}/{zipcode}/{houseNumber}")
    public ResponseEntity<List<ShoppingCartDto>> getShoppingCartsByNameAndAddress(@PathVariable String firstName, @PathVariable String lastName,
                                                                                  @PathVariable String zipcode, @PathVariable int houseNumber) {
        return ResponseEntity.ok(shoppingCartService.getShoppingCartsByNameAndAddress(firstName, lastName, zipcode, houseNumber));
    }

    @GetMapping("/{firstName}/{lastName}/{zipcode}/{houseNumber}/{additionalHouseNumber}")
    public ResponseEntity<List<ShoppingCartDto>> getShoppingCartsByNameAndAddress2(@PathVariable String firstName, @PathVariable String lastName,
                                                                                  @PathVariable String zipcode, @PathVariable int houseNumber,
                                                                                  @PathVariable String additionalHouseNumber) {
        return ResponseEntity.ok(shoppingCartService.getShoppingCartsByNameAndAddress(firstName, lastName, zipcode, houseNumber, additionalHouseNumber));
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ShoppingCartDto> createShoppingCart(@RequestBody ShoppingCartInputDto shoppingCartInputDto) {
        final URI location = URI.create("/shoppingcart" + shoppingCartInputDto.getCustomerId());
        return ResponseEntity.created(location).body(shoppingCartService.createShoppingCard(shoppingCartInputDto));
    }

    @DeleteMapping("/{id}")
    public void deleteShoppingCart(@PathVariable long id) {
        shoppingCartService.deleteShoppingCart(id);
    }
}
