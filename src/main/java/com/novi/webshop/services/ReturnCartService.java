package com.novi.webshop.services;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ReturnCartDto;
import com.novi.webshop.model.Orders;
import com.novi.webshop.model.Product;

import java.util.List;

public interface ReturnCartService {
    List<ReturnCartDto> getAllReturnCarts();

    List<ReturnCartDto> getProcessedOrNotProcessedReturnCarts(boolean processedOrNotProcessed);

    List<ReturnCartDto> getReturnCartByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber);

    List<ReturnCartDto> getReturnCartByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber, String additionalHouseNumber);

    ReturnCartDto changeProcessedStatus(Long id, boolean processed);

    ReturnCartDto createReturnProducts(Long orderId);

    default boolean within30DaysReturnTime(Orders order) {
        long maximumReturnTime = order.getOrderDateInMilliSeconds() + 1000L * 60 * 60 * 24 * 30;
        long currentTime = System.currentTimeMillis();
        if (currentTime > maximumReturnTime) {
            return false;
        } else {
            return true;
        }
    }

    ReturnCartDto connectProductWithReturnCart(Long returnCartId, Long productId, ProductDto productDto);

}
