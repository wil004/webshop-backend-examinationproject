package com.novi.webshop.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.novi.webshop.dto.ReturnCartDto;
import com.novi.webshop.model.Orders;
import com.novi.webshop.model.ReturnCart;
import com.novi.webshop.repository.ReturnCartRepository;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ReturnCartServiceImplTest {

    @Autowired
    ReturnCartServiceImpl returnCartService;
    @Autowired
    private ReturnCartRepository returnCartRepository;

    // NOT ENOUGH KNOWLEDGE TO DO COMPLICATED TESTS WITH RELATIONSHIPS IN IT ON SERVER LAYER!!!!!!!!!

    @Test
    void getProcessedOrNotProcessedReturnCarts() {
        ReturnCart returnCart = new ReturnCart();
        returnCart.setProcessed(false);
        returnCartRepository.save(returnCart);

        List<ReturnCartDto> returnCartDtoList = returnCartService.getProcessedOrNotProcessedReturnCarts(false);

        assertFalse(returnCartDtoList.get(0).isProcessed());
    }
}