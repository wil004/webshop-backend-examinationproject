package com.novi.webshop.controller;

import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/processed-status={processed}")
    public ResponseEntity<List<OrderDto>> getAllProcessedOrNotProcessedOrders(@PathVariable boolean processed) {
        return ResponseEntity.ok(orderService.getProcessedOrNotProcessedOrders(processed));
    }

    @GetMapping("/{firstName}/{lastName}/{zipcode}/{houseNumber}")
    public ResponseEntity<List<OrderDto>> getOrdersByNameAndAddress(@PathVariable String firstName, @PathVariable String lastName,
                                                                                  @PathVariable String zipcode, @PathVariable int houseNumber) {
        return ResponseEntity.ok(orderService.getOrdersByNameAndAddress(firstName, lastName, zipcode, houseNumber));
    }

    @GetMapping("/{firstName}/{lastName}/{zipcode}/{houseNumber}/{additionalHouseNumber}")
    public ResponseEntity<List<OrderDto>> getOrdersByNameAndAddress2(@PathVariable String firstName, @PathVariable String lastName,
                                                                                   @PathVariable String zipcode, @PathVariable int houseNumber,
                                                                                   @PathVariable String additionalHouseNumber) {
        return ResponseEntity.ok(orderService.getOrdersByNameAndAddress(firstName, lastName, zipcode, houseNumber, additionalHouseNumber));
    }

    @PutMapping(value = "change-processed-status={processed}/id={id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderDto> changeProcessedStatus(@PathVariable Long id, @PathVariable boolean processed) {
        return ResponseEntity.ok(orderService.changeProcessedStatus(id, processed));
    }

    @PostMapping(path = "/customer={customerId}",consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<OrderDto> createOrderFromCustomer(@PathVariable Long customerId) {
        final URI location = URI.create("/order" + customerId);
        return ResponseEntity.created(location).body(orderService.createOrderFromCustomer(customerId));
    }

    @PostMapping(path = "/guest={customerId}",consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<OrderDto> createOrderFromGuest(@PathVariable Long customerId, @RequestBody ShoppingCartDto shoppingCartDto) {
        final URI location = URI.create("/order" + customerId);
        return ResponseEntity.created(location).body(orderService.createOrderFromGuestCustomer(customerId, shoppingCartDto));
    }

}
