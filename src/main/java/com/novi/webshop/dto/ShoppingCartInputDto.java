package com.novi.webshop.dto;

import java.util.List;

public class ShoppingCartInputDto {
    private Long id;

    private long customerId;


    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


}
