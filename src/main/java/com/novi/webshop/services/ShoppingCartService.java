package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.model.Admin;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Product;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.ProductRepository;
import com.novi.webshop.repository.ShoppingCartRepository;
import com.novi.webshop.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {

private final ShoppingCartRepository shoppingCartRepository;
private final ProductRepository productRepository;
private final CustomerRepository customerRepository;
private final CustomerService customerService;
private final AdminRepository adminRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, CustomerRepository customerRepository, CustomerService customerService, AdminRepository adminRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.adminRepository = adminRepository;
    }

    public List<ShoppingCartDto> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        List<ShoppingCartDto> shoppingCartDtoList = new ArrayList<>();
        for (int i = 0; i < shoppingCartList.size(); i++) {
            shoppingCartDtoList.add(transferToShoppingCartDto(shoppingCartList.get(i)));
        }
        return shoppingCartDtoList;
    }

    public List<ShoppingCartDto> getProcessedOrNotProcessedShoppingCarts(boolean processedOrNotProcessed) {
        List<ShoppingCart> allShoppingCartList = shoppingCartRepository.findAll();
        List<ShoppingCart> allShoppingCartsWithProcessedStatus = new ArrayList<>();

            for (int i = 0; i < allShoppingCartList.size(); i++) {
                if(processedOrNotProcessed) {
                    if (allShoppingCartList.get(i).isProcessed()) {
                        allShoppingCartsWithProcessedStatus.add(allShoppingCartList.get(i));
                        Admin admin = new Admin();
                        admin.setAllProcessedShoppingCarts(allShoppingCartsWithProcessedStatus);
                        adminRepository.save(admin);
                    }
                } else {
                    if (!allShoppingCartList.get(i).isProcessed()) {
                        allShoppingCartsWithProcessedStatus.add(allShoppingCartList.get(i));
                        Admin admin = new Admin();
                        admin.setAllNotProcessedShoppingCarts(allShoppingCartsWithProcessedStatus);
                        adminRepository.save(admin);
                    }
                }
                }
        List<ShoppingCartDto> shoppingCartDtoList = new ArrayList<>();
        for (int i = 0; i < allShoppingCartsWithProcessedStatus.size(); i++) {
            shoppingCartDtoList.add(transferToShoppingCartDto(allShoppingCartsWithProcessedStatus.get(i)));
        }
        return shoppingCartDtoList;
    }

    public ShoppingCartDto getShoppingCartById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow();
        if(shoppingCartRepository.findById(id).isPresent()) {
            return transferToShoppingCartDto(shoppingCart);
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
        List<ShoppingCartDto> shoppingCartDtoList = new ArrayList<>();
        for (int i = 0; i < foundShoppingCarts.size(); i++) {
            shoppingCartDtoList.add(transferToShoppingCartDto(foundShoppingCarts.get(i)));
        }
        return shoppingCartDtoList;
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
        List<ShoppingCartDto> shoppingCartDtoList = new ArrayList<>();
        for (int i = 0; i < foundShoppingCarts.size(); i++) {
            shoppingCartDtoList.add(transferToShoppingCartDto(foundShoppingCarts.get(i)));
        }
        return shoppingCartDtoList;
    }


    public ShoppingCartDto changeProcessedStatus(Long id, boolean processed) {
        if(shoppingCartRepository.findById(id).isPresent()) {
           ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow();
                shoppingCart.setProcessed(processed);
            ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
                return transferToShoppingCartDto(savedShoppingCart);
        }
        else {
            throw new RecordNotFoundException("Couldn't find shopping-cart");
        }
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

            product.setAmountOfProducts(productDto.getAmountOfProducts());
            productsInShoppingCartObject.add(product);
            shoppingCart.setProductList(productsInShoppingCartObject);
            shoppingCart.setTotalPrice(shoppingCart.getTotalPrice() + product.getSellingPrice() * productDto.getAmountOfProducts());
            shoppingCartRepository.save(shoppingCart);


            return transferToShoppingCartDto(shoppingCart);
        } else {
            throw new RecordNotFoundException("ShoppingCart Id Or Product Id doesn't exist!");
        }
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
        if(shoppingCart.getProductList() != null) {
            List<ProductDto> productDtoList = new ArrayList<>();
            for(int i = 0; i < shoppingCart.getProductList().size(); i++) {
                productDtoList.add(transferToProductDto(shoppingCart.getProductList().get(i)));
            }
            shoppingCartDto.setProduct(productDtoList);
        }
        return shoppingCartDto;
    }

    protected ProductDto transferToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProduct(product.getProduct());
        productDto.setCategory(product.getCategory());
        productDto.setSellingPrice(product.getSellingPrice());
        productDto.setRetailPrice(product.getRetailPrice());
        productDto.setAmountOfProducts(product.getAmountOfProducts());
        return productDto;
    }
}
