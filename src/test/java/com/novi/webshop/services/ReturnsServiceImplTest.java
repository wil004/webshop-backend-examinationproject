package com.novi.webshop.services;

import com.novi.webshop.dto.ReturnsDto;
import com.novi.webshop.model.Returns;
import com.novi.webshop.repository.ReturnsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ReturnsServiceImplTest {

    @Autowired
    ReturnsServiceImpl returnCartService;
    @Autowired
    private ReturnsRepository returnsRepository;

    @Test
    void getProcessedOrNotProcessedReturnCarts() {
        Returns aReturns = new Returns();
        aReturns.setProcessed(false);
        returnsRepository.save(aReturns);

        List<ReturnsDto> returnsDtoList = returnCartService.getProcessedOrNotProcessedReturns(false);

        assertFalse(returnsDtoList.get(0).isProcessed());
    }
}