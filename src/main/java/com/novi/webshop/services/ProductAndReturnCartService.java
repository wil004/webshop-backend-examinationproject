package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ProductAndReturnCartDto;
import com.novi.webshop.model.ProductAndReturnCart;
import com.novi.webshop.model.ProductAndShoppingCart;
import com.novi.webshop.model.ReturnCart;
import com.novi.webshop.repository.ProductAndReturnCartRepository;
import com.novi.webshop.repository.ReturnCartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAndReturnCartService {

    private final ProductAndReturnCartRepository productAndReturnCartRepository;
    private final ReturnCartRepository returnCartRepository;
    private final ProductAndShoppingCartService productAndShoppingCartService;


    public ProductAndReturnCartService(ProductAndReturnCartRepository productAndReturnCartRepository, ReturnCartRepository returnCartRepository, ProductAndShoppingCartService productAndShoppingCartService) {
        this.productAndReturnCartRepository = productAndReturnCartRepository;
        this.returnCartRepository = returnCartRepository;
        this.productAndShoppingCartService = productAndShoppingCartService;
    }

    public ProductAndReturnCartDto connectProductWithReturnCart(Long productId, Long returnCartId, ProductAndReturnCartDto productAndReturnCartDto) {
        ReturnCart returnCart = returnCartRepository.findById(productId).orElseThrow();
        if (returnCartRepository.findById(returnCartId).isPresent()) {
            for (int i = 0; i < returnCart.getProductAndReturnCartList().size(); i++) {
                for (int j = 0; j < returnCart.getShoppingCart().getProductAndShoppingCarts().size(); j++)
                    if (returnCart.getProductAndReturnCartList().get(i).getProduct().equals(returnCart.getShoppingCart().getProductAndShoppingCarts().get(j).getProduct())) {
                        if (productAndReturnCartDto.getAmountOfProduct() <= returnCart.getShoppingCart().getProductAndShoppingCarts().get(j).getAmountOfProduct()) {
                            productAndShoppingCartService.changeAmountOfProduct(returnCart.getShoppingCart().getProductAndShoppingCarts().get(j).getId(), productAndReturnCartDto.getAmountOfProduct());

                            ProductAndReturnCart productAndReturnCart = new ProductAndReturnCart();
                            productAndReturnCart.setReturnCart(returnCart);
                            productAndReturnCart.setProduct(returnCart.getShoppingCart().getProductAndShoppingCarts().get(j).getProduct());

                            List<ProductAndReturnCart> productAndReturnCartList = returnCart.getProductAndReturnCartList();
                            productAndReturnCartList.add(productAndReturnCart);
                            returnCart.setProductAndReturnCartList(productAndReturnCartList);

                            productAndReturnCartRepository.save(productAndReturnCart);
                            returnCartRepository.save(returnCart);
                            return transferToProductAndReturnCartDto(returnCart.getProductAndReturnCartList().get(i));
                        } else {
                            throw new RecordNotFoundException("You cannot return more products then you ordered");
                        }
                    } else {
                        throw new RecordNotFoundException("Couldn't find the product you want to return");
                    }
            }
        }
            throw new RecordNotFoundException("Couldn't find return cart");
    }

    public ProductAndReturnCartDto transferToProductAndReturnCartDto(ProductAndReturnCart productAndReturnCart) {
        ProductAndReturnCartDto productAndReturnCartDto = new ProductAndReturnCartDto();
        productAndReturnCartDto.setProductId(productAndReturnCart.getProduct().getId());
        productAndReturnCartDto.setReturnCartId(productAndReturnCart.getReturnCart().getId());
        productAndReturnCartDto.setAmountOfProduct(productAndReturnCart.getAmountOfProduct());
        return productAndReturnCartDto;
    }
}

