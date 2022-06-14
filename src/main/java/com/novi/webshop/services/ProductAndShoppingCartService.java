package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ProductAndShoppingCartDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.model.Product;
import com.novi.webshop.model.ProductAndShoppingCart;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.ProductAndShoppingCartRepository;
import com.novi.webshop.repository.ProductRepository;
import com.novi.webshop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductAndShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final ProductAndShoppingCartRepository productAndShoppingCartRepository;
    private final CustomerService customerService;


    @Autowired
    public ProductAndShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, ProductAndShoppingCartRepository productAndShoppingCartRepository, CustomerService customerService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.productAndShoppingCartRepository = productAndShoppingCartRepository;
        this.customerService = customerService;
    }



    public ProductAndShoppingCartDto connectProductWithShoppingCart(ProductAndShoppingCartDto productAndShoppingCartDto) {
        boolean productExists = false;
        int existingProductIndex = 0;
        boolean shoppingCartExists = false;
        int existingShoppingCartIndex = 0;

        List<Product> products = productRepository.findAll();
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();

        for (int i = 0; i < products.size(); i++) {
            if (Objects.equals(products.get(i).getId(), productAndShoppingCartDto.getProductId())) {
                productExists = true;
                existingProductIndex = i;
            }
        }

        for (int i = 0; i < shoppingCarts.size(); i++) {
            if (Objects.equals(shoppingCarts.get(i).getId(), productAndShoppingCartDto.getShoppingCartId())) {
                shoppingCartExists = true;
                existingShoppingCartIndex = i;
            }
        }


        if (productExists && shoppingCartExists) {
            boolean relationAlreadyExists = false;

            ProductAndShoppingCart productAndShoppingCart = new ProductAndShoppingCart();
            Product newProduct = products.get(existingProductIndex);
            ShoppingCart newShoppingCart = shoppingCarts.get(existingShoppingCartIndex);

            List<ProductAndShoppingCart> productAndShoppingCartProducts = newProduct.getProductAndShoppingCarts();
            List<ProductAndShoppingCart> productAndShoppingCartShoppingCarts = newShoppingCart.getProductAndShoppingCarts();

            for (int i = 0; i < productAndShoppingCartProducts.size(); i++) {
                if (Objects.equals(productAndShoppingCartProducts.get(i).getProduct().getId(), productAndShoppingCartDto.getProductId())
                        && Objects.equals(productAndShoppingCartProducts.get(i).getShoppingCart().getId(), productAndShoppingCartDto.getShoppingCartId())) {
                    relationAlreadyExists = true;
                }
            }
            if (relationAlreadyExists) {
                throw new RecordNotFoundException("This relation already exists");
            }

            productAndShoppingCart.setProduct(newProduct);
            productAndShoppingCart.setShoppingCart(newShoppingCart);

            productAndShoppingCartProducts.add(productAndShoppingCart);
            productAndShoppingCartShoppingCarts.add(productAndShoppingCart);

            newProduct.setProductAndShoppingCarts(productAndShoppingCartProducts);
            newShoppingCart.setProductAndShoppingCarts(productAndShoppingCartShoppingCarts);


            productRepository.save(newProduct);
            shoppingCartRepository.save(newShoppingCart);
            productAndShoppingCartRepository.save(productAndShoppingCart);

            return productAndShoppingCartDto;
        } else {
            throw new RecordNotFoundException("ShoppingCart Id Or Product Id doesn't exist!");
        }
    }


    public List<ShoppingCartDto> addProductsToShoppingCarts(List<ShoppingCart> shoppingCartList) {
        List<ShoppingCartDto> shoppingCartDtoList = new ArrayList<>();
        for (int i = 0; i < shoppingCartList.size(); i++) {
            shoppingCartDtoList.add(transferToShoppingCartDto(shoppingCartList.get(i)));
                List<ProductDto> productDtoList = new ArrayList<>();
                for (int j = 0; j < shoppingCartList.get(i).getProductAndShoppingCarts().size(); j++) {
                    productDtoList.add(transferToProductDto(shoppingCartList.get(i).getProductAndShoppingCarts().get(j).getProduct()));
                }
                shoppingCartDtoList.get(i).setProduct(productDtoList);
        }
        return shoppingCartDtoList;
    }


    private ShoppingCartDto transferToShoppingCartDto(ShoppingCart shoppingCart) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(shoppingCart.getId());
        shoppingCartDto.setTotalPrice(shoppingCart.getTotalPrice());
        shoppingCartDto.setProcessed(false);
        shoppingCartDto.setCustomerDto(customerService.transferToCustomerDto(shoppingCart.getCustomer()));
        return shoppingCartDto;
    }

    private ProductDto transferToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProduct(product.getProduct());
        productDto.setCategory(product.getCategory());
        productDto.setSellingPrice(product.getSellingPrice());
        productDto.setRetailPrice(product.getRetailPrice());
        return productDto;
    }

}
