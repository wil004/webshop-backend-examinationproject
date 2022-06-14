package com.novi.webshop.dto;

import com.novi.webshop.model.Product;
import com.novi.webshop.model.ShoppingCart;

public class ProductAndShoppingCartDto {

    private int amountOfProduct;
    private Long productId;
    private Long shoppingCartId;


    public int getAmountOfProduct() {
        return amountOfProduct;
    }
    public void setAmountOfProduct(int amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }
}
