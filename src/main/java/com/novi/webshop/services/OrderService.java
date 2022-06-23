package com.novi.webshop.services;

import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.dto.ShoppingCartDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();

    List<OrderDto> getProcessedOrNotProcessedOrders(boolean processedOrNotProcessed);

    OrderDto getOrderById(Long id);

    List<OrderDto> getOrdersByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber);

    List<OrderDto> getOrdersByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber, String additionalHouseNumber);

    OrderDto createOrderFromCustomer(Long customerId);

    OrderDto createOrderFromGuestCustomer(Long customerId, ShoppingCartDto shoppingCartDto);

    OrderDto changeProcessedStatus(Long id, boolean processed);
}
