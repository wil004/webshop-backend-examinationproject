package com.novi.webshop.dto;


public class ProductAndReturnCartDto {

    private int amountOfProduct;
    private Long productId;

    private Long returnCartId;

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

    public Long getReturnCartId() {
        return returnCartId;
    }

    public void setReturnCartId(Long returnCartId) {
        this.returnCartId = returnCartId;
    }
}
