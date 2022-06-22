package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Product;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.ProductRepository;
import com.novi.webshop.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {

private final ShoppingCartRepository shoppingCartRepository;
private final ProductRepository productRepository;
private final CustomerRepository customerRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public ShoppingCartDto connectProductWithShoppingCart(Long shoppingCartId, Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).orElseThrow();
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow();



        if (productRepository.findById(productId).isPresent() && shoppingCartRepository.findById(shoppingCartId).isPresent()) {
            List<ShoppingCart> shoppingCartsInProductObject = product.getShoppingCartList();
            List<Product> productsInShoppingCartObject = shoppingCart.getProductList();

            for (int i = 0; i < shoppingCart.getProductList().size(); i++)
                if (shoppingCart.getProductList().get(i) == product) {
                    throw new RecordNotFoundException("This relation already exists");
                }

            shoppingCartsInProductObject.add(shoppingCart);
            product.setShoppingCartList(shoppingCartsInProductObject);
            productRepository.save(product);

            product.setAmountOfOrderedProducts(productDto.getAmountOfOrderedProducts());
            productsInShoppingCartObject.add(product);
            shoppingCart.setProductList(productsInShoppingCartObject);
            shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + product.getSellingPrice() * productDto.getAmountOfOrderedProducts());
            shoppingCartRepository.save(shoppingCart);


            return TransferModelToDto.transferToShoppingCartDto(shoppingCart);
        } else {
            throw new RecordNotFoundException("ShoppingCart Id Or Product Id doesn't exist!");
        }
    }

    public ShoppingCartDto createShoppingCard(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        if (customer.getUsername() != null && customer.getShoppingCart() == null) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setCustomer(customer);
            customer.setShoppingCart(shoppingCart);
            ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);

            customerRepository.save(customer);
            return TransferModelToDto.transferToShoppingCartDto(savedShoppingCart);
        } else {
            throw new RecordNotFoundException("Customer account doesn't exist or is a guest.");
        }
    }

    public void deleteShoppingCart(Long id) {
        if (shoppingCartRepository.findById(id).isPresent()) {
            shoppingCartRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("You cannot delete a shopping cart that does not exists");
        }
    }

    protected ShoppingCart transferToShoppingCart(ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartDto.getId());
        shoppingCart.setTotalPrice(shoppingCartDto.getTotalPrice());

        return shoppingCart;
    }

}
