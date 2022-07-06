package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Product;
import com.novi.webshop.model.QuantityAndProduct;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.ProductRepository;
import com.novi.webshop.repository.QuantityAndProductRepository;
import com.novi.webshop.repository.ShoppingCartRepository;
import com.novi.webshop.security.JwtRequestFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

private final ShoppingCartRepository shoppingCartRepository;
private final ProductRepository productRepository;
private final CustomerRepository customerRepository;
private final QuantityAndProductRepository quantityAndProductRepository;
private final UserServiceImpl userServiceImpl;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, CustomerRepository customerRepository, QuantityAndProductRepository quantityAndProductRepository, UserServiceImpl userServiceImpl) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.quantityAndProductRepository = quantityAndProductRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public ShoppingCartDto connectProductWithShoppingCart(Long customerId, Long productId, ProductDto productDto) {
        if(!Objects.equals(customerId, userServiceImpl.findIdFromUsername(JwtRequestFilter.getUsername()))
                && !Objects.equals(userServiceImpl.findRoleFromUsername(JwtRequestFilter.getUsername()), "ADMIN")) {
            throw new RecordNotFoundException("Customer has only acces to his own data");
        }
        Long shoppingCartId;
        if(customerRepository.findById(customerId).isPresent()) {
            Customer customer = customerRepository.findById(customerId).orElseThrow();
            if(customer.getShoppingCart().getId() != null) {
                shoppingCartId = customer.getShoppingCart().getId();
            } else {
                throw new RecordNotFoundException("This customer has no shoppingcart because he is a guest");
            }
        } else {
            throw new RecordNotFoundException("Customer doesn't exist");
        }
        Product product = productRepository.findById(productId).orElseThrow();
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow();

        if (productRepository.findById(productId).isPresent() && shoppingCartRepository.findById(shoppingCartId).isPresent()) {

            List<QuantityAndProduct> productsInShoppingCartObject = shoppingCart.getQuantityAndProductList();

            if(productDto.getAmountOfProducts() <= 0) {
                throw new RecordNotFoundException("No valid amount of products");
            }
            for (int i = 0; i < shoppingCart.getQuantityAndProductList().size(); i++) {
                if (shoppingCart.getQuantityAndProductList().get(i).getProduct() == product) {
                    throw new RecordNotFoundException("This relation already exists");
                }
            }
            QuantityAndProduct quantityAndProduct = new QuantityAndProduct();
            quantityAndProduct.setProduct(product);
            quantityAndProduct.setAmountOfProducts(productDto.getAmountOfProducts());
            quantityAndProduct.setShoppingCart(shoppingCart);
            productsInShoppingCartObject.add(quantityAndProduct);

            quantityAndProductRepository.save(quantityAndProduct);
            shoppingCart.setQuantityAndProductList(productsInShoppingCartObject);
            shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + product.getPrice() * productDto.getAmountOfProducts());
            shoppingCartRepository.save(shoppingCart);

            return TransferModelToDto.transferToShoppingCartDto(shoppingCart);
        } else {
            throw new RecordNotFoundException("ShoppingCart Id Or Product Id doesn't exist!");
        }
    }

    @Override
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

    @Override
    public void deleteShoppingCart(Long id) {
        if (shoppingCartRepository.findById(id).isPresent()) {
            shoppingCartRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("You cannot delete a shopping cart that does not exists");
        }
    }

}
