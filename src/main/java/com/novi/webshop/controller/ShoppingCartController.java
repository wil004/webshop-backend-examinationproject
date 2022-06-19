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

    @GetMapping
    public ResponseEntity<List<ShoppingCartDto>> getAllShoppingCarts() {
        return ResponseEntity.ok(shoppingCartService.getAllShoppingCarts());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ShoppingCartDto> getShoppingCartById(@PathVariable Long id) {
        return ResponseEntity.ok(shoppingCartService.getShoppingCartById(id));
    }

    @GetMapping("/processed-status={processed}")
    public ResponseEntity<List<ShoppingCartDto>> getAllProcessedOrNotProcessedShoppingCarts(@PathVariable boolean processed) {
        return ResponseEntity.ok(shoppingCartService.getProcessedOrNotProcessedShoppingCarts(processed));
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

    @PutMapping(value = "change-processed-status={processed}/id={id}",
    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ShoppingCartDto> changeProcessedStatus(@PathVariable Long id, @PathVariable boolean processed) {
        return ResponseEntity.ok(shoppingCartService.changeProcessedStatus(id, processed));
    }


    @PutMapping(value = "id={shoppingCartId}/productid={productId}",
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ShoppingCartDto> addProductToShoppingCart(@PathVariable Long shoppingCartId ,@PathVariable Long productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(shoppingCartService.connectProductWithShoppingCart(shoppingCartId, productId, productDto));
    }

    @PostMapping(path = "/{customerId}",consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ShoppingCartDto> createShoppingCart(@PathVariable Long customerId) {
        final URI location = URI.create("/shoppingcart" + customerId);
        return ResponseEntity.created(location).body(shoppingCartService.createShoppingCard(customerId));
    }



    @DeleteMapping("/{id}")
    public void deleteShoppingCart(@PathVariable long id) {
        shoppingCartService.deleteShoppingCart(id);
    }
}
