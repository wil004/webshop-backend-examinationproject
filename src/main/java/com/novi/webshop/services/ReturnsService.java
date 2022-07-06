package com.novi.webshop.services;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ReturnsDto;

import java.util.List;

public interface ReturnsService {
    List<ReturnsDto> getAllReturns();

    List<ReturnsDto> getProcessedOrNotProcessedReturns(boolean processedOrNotProcessed);

    List<ReturnsDto> getReturnByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber);

    List<ReturnsDto> getReturnByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber, String additionalHouseNumber);

    Object changeProcessedStatus(Long id, boolean processed);

    ReturnsDto createReturnProducts(Long orderId, ReturnsDto returnsDto);

    ReturnsDto connectProductWithReturn(Long returnCartId, Long productId, ProductDto productDto);

}
