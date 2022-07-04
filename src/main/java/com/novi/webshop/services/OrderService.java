package com.novi.webshop.services;

import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.dto.UserEmployeeDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();

    OrderDto orderIsPaid(Long orderId);

    OrderDto changeProcessedStatus(Long id, boolean processed);

    List<OrderDto> getAllProcessedOrNotProcessedOrders(boolean processedOrNotProcessed);

    OrderDto getOrderById(Long id);

    List<OrderDto> getOrdersByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber);

    List<OrderDto> getOrdersByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber, String additionalHouseNumber);

    OrderDto createOrderFromCustomer(Long customerId);

    OrderDto createOrderFromGuestCustomer(Long customerId, ShoppingCartDto shoppingCartDto);

    public void deleteOrder(Long orderId);
}
