package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.dto.ShoppingCartInputDto;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {

private final ShoppingCartRepository shoppingCartRepository;
private final CustomerRepository customerRepository;
private final CustomerService customerService;
private final ProductAndShoppingCartService productAndShoppingCartService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, CustomerRepository customerRepository, CustomerService customerService, ProductAndShoppingCartService productAndShoppingCartService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.productAndShoppingCartService = productAndShoppingCartService;
    }

    public List<ShoppingCartDto> getAllShoppingCarts() {
        return productAndShoppingCartService.addProductsToShoppingCarts(shoppingCartRepository.findAll());
    }

    public List<ShoppingCartDto> getStillToProcessShoppingCarts() {
        List<ShoppingCart> allShoppingCartList = shoppingCartRepository.findAll();
        List<ShoppingCart> allNotProcessedShoppingCarts = new ArrayList<>();
        for (int i = 0; i < allShoppingCartList.size(); i++) {
            if (!allShoppingCartList.get(i).isProcessed()) {
                allNotProcessedShoppingCarts.add(allShoppingCartList.get(i));
            }
        }
        return productAndShoppingCartService.addProductsToShoppingCarts(allNotProcessedShoppingCarts);
    }

    public ShoppingCartDto getShoppingCartById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow();
        if(shoppingCartRepository.findById(id).isPresent()) {
            return productAndShoppingCartService.addProductsToShoppingCart(shoppingCart);
        } else {
            throw new RecordNotFoundException("Couldn't find shopping cart");
        }
    }


    public List<ShoppingCartDto> getShoppingCartsByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber) {
        List<ShoppingCart> allShoppingCartList = shoppingCartRepository.findAll();
        List<ShoppingCart> foundShoppingCarts = new ArrayList<>();
        for(int i = 0; i < allShoppingCartList.size(); i++) {
            if (allShoppingCartList.get(i).getCustomer().getFirstName().equalsIgnoreCase(firstName) && allShoppingCartList.get(i).getCustomer().getLastName().equalsIgnoreCase(lastName) &&
                    allShoppingCartList.get(i).getCustomer().getZipcode().equalsIgnoreCase(zipcode) && allShoppingCartList.get(i).getCustomer().getHouseNumber() == houseNumber &&
                    allShoppingCartList.get(i).getCustomer().getAdditionalToHouseNumber() == null) {
                foundShoppingCarts.add(allShoppingCartList.get(i));
            }
        }
        if(foundShoppingCarts.size() == 0) {
            throw new RecordNotFoundException("No shopping cart found!");
        }
        return productAndShoppingCartService.addProductsToShoppingCarts(foundShoppingCarts);
    }
    public List<ShoppingCartDto> getShoppingCartsByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber, String additionalHouseNumber) {
        List<ShoppingCart> allShoppingCartList = shoppingCartRepository.findAll();
        List<ShoppingCart> foundShoppingCarts = new ArrayList<>();
        for(int i = 0; i < allShoppingCartList.size(); i++) {
            if(allShoppingCartList.get(i).getCustomer().getAdditionalToHouseNumber() != null) {
                if (allShoppingCartList.get(i).getCustomer().getFirstName().equalsIgnoreCase(firstName) && allShoppingCartList.get(i).getCustomer().getLastName().equalsIgnoreCase(lastName) &&
                        allShoppingCartList.get(i).getCustomer().getZipcode().equalsIgnoreCase(zipcode) && allShoppingCartList.get(i).getCustomer().getHouseNumber() == houseNumber &&
                        allShoppingCartList.get(i).getCustomer().getAdditionalToHouseNumber().equalsIgnoreCase(additionalHouseNumber)) {
                    foundShoppingCarts.add(allShoppingCartList.get(i));
                }
            }
        }
        if(foundShoppingCarts.size() == 0) {
            throw new RecordNotFoundException("No shopping cart found!");
        }
        return productAndShoppingCartService.addProductsToShoppingCarts(foundShoppingCarts);
    }




    public ShoppingCartDto createShoppingCard(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProcessed(false);
        shoppingCart.setOrderDate(LocalDateTime.now());
        shoppingCart.setOrderDateInMilliSeconds(System.currentTimeMillis());
        shoppingCart.setCustomer(customer);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        ShoppingCartDto shoppingCartDto = transferToShoppingCartDto(savedShoppingCart);
        shoppingCartDto.setCustomerDto(customerService.transferToCustomerDto(customer));
        return shoppingCartDto;
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
        shoppingCart.setProcessed(shoppingCartDto.isProcessed());
        shoppingCart.setOrderDate(shoppingCartDto.getOrderDate());
        shoppingCart.setCustomer(shoppingCart.getCustomer());
        return shoppingCart;
    }
    protected ShoppingCartDto transferToShoppingCartDto(ShoppingCart shoppingCart) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(shoppingCart.getId());
        shoppingCartDto.setTotalPrice(shoppingCart.getTotalPrice());
        shoppingCartDto.setProcessed(shoppingCart.isProcessed());
        shoppingCartDto.setOrderDate(shoppingCart.getOrderDate());
        shoppingCartDto.setCustomerDto(customerService.transferToCustomerDto(shoppingCart.getCustomer()));
        return shoppingCartDto;
    }
}
