package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ReturnCartDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.Orders;
import com.novi.webshop.model.Product;
import com.novi.webshop.model.ReturnCart;
import com.novi.webshop.repository.OrderRepository;
import com.novi.webshop.repository.ProductRepository;
import com.novi.webshop.repository.ReturnCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturnCartServiceImpl implements ReturnCartService {

    private final ReturnCartRepository returnCartRepository;
    private final ProductRepository productRepository;


    private final OrderRepository orderRepository;

    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public ReturnCartServiceImpl(ReturnCartRepository returnCartRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderServiceImpl orderServiceImpl) {
        this.returnCartRepository = returnCartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderServiceImpl = orderServiceImpl;
    }

    @Override
    public List<ReturnCartDto> getAllReturnCarts() {
        List<ReturnCart> returnCartList = returnCartRepository.findAll();
        List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
        for (int i = 0; i < returnCartList.size(); i++) {
            returnCartDtoList.add(TransferModelToDto.transferToReturnCartDto(returnCartList.get(i)));
        }
        return returnCartDtoList;
    }

    @Override
    public List<ReturnCartDto> getProcessedOrNotProcessedReturnCarts(boolean processedOrNotProcessed) {
        List<ReturnCart> allReturnCartList = returnCartRepository.findAll();
        List<ReturnCart> allReturnCartsWithProcessedStatus = new ArrayList<>();
            for (int i = 0; i < allReturnCartList.size(); i++) {
                if (processedOrNotProcessed) {
                    if (allReturnCartList.get(i).isProcessed()) {
                        allReturnCartsWithProcessedStatus.add(allReturnCartList.get(i));
                    }
                } else {
                    if (!allReturnCartList.get(i).isProcessed()) {
                        allReturnCartsWithProcessedStatus.add(allReturnCartList.get(i));
                    }
                }
            }

            List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
            for (int i = 0; i < allReturnCartsWithProcessedStatus.size(); i++) {
                returnCartDtoList.add(TransferModelToDto.transferToReturnCartDto(allReturnCartsWithProcessedStatus.get(i)));
            }

            return returnCartDtoList;
        }

    @Override
    public List<ReturnCartDto> getReturnCartByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber) {
        List<OrderDto> orderDtoList = orderServiceImpl.getOrdersByNameAndAddress(firstName, lastName, zipcode, houseNumber);
        List<Orders> orderList = new ArrayList<>();
        List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
        for (int i = 0; i < orderDtoList.size(); i++) {
            if(orderRepository.findById(orderDtoList.get(i).getId()).isPresent()) {
                orderList.add(orderRepository.findById(orderDtoList.get(i).getId()).orElseThrow());
            }
            for (int j = 0; j < orderList.get(i).getReturnList().size(); j++) {
                returnCartDtoList.add(TransferModelToDto.transferToReturnCartDto(orderList.get(i).getReturnList().get(j)));
            }
        }
        return returnCartDtoList;
    }

    @Override
    public List<ReturnCartDto> getReturnCartByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber, String additionalHouseNumber) {
        List<OrderDto> orderDtoList = orderServiceImpl.getOrdersByNameAndAddress(firstName, lastName, zipcode, houseNumber, additionalHouseNumber);
        List<Orders> orderList = new ArrayList<>();
        List<ReturnCartDto> returnCartDtoList = new ArrayList<>();
        for (int i = 0; i < orderDtoList.size(); i++) {
            if(orderRepository.findById(orderDtoList.get(i).getId()).isPresent()) {
                orderList.add(orderRepository.findById(orderDtoList.get(i).getId()).orElseThrow());
            }
            for (int j = 0; j < orderList.get(i).getReturnList().size(); j++) {
                returnCartDtoList.add(TransferModelToDto.transferToReturnCartDto(orderList.get(i).getReturnList().get(j)));
            }
        }
        return returnCartDtoList;
    }

    @Override
    public ReturnCartDto changeProcessedStatus(Long id, boolean processed) {
        if(returnCartRepository.findById(id).isPresent()) {
            ReturnCart returnCart = returnCartRepository.findById(id).orElseThrow();
            returnCart.setProcessed(processed);
            ReturnCart savedReturnCart = returnCartRepository.save(returnCart);
            return TransferModelToDto.transferToReturnCartDto(savedReturnCart);
        }
        else {
            throw new RecordNotFoundException("Couldn't find return-cart");
        }
    }

    @Override
    public ReturnCartDto createReturnProducts(Long orderId) {
        ReturnCart returnCart = new ReturnCart();
        Orders order = orderRepository.findById(orderId).orElseThrow();
        if (orderRepository.findById(orderId).isPresent()) {
            returnCart.setCustomerOrder(order);
            if (within30DaysReturnTime(returnCart.getCustomerOrder())) {
                ReturnCart savedReturnCart = returnCartRepository.save(returnCart);
                return TransferModelToDto.transferToReturnCartDto(savedReturnCart);
            } else {
                throw new RecordNotFoundException("Returning time has been expired, you can't return products anymore!");
            }
        } else {
            throw new RecordNotFoundException("Couldn't connect a shopping cart to the return cart");
        }
    }


    @Override
    public ReturnCartDto connectProductWithReturnCart(Long returnCartId, Long productId, ProductDto productDto) {
        ReturnCart returnCart = returnCartRepository.findById(returnCartId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        Orders order = returnCart.getCustomerOrder();

        if (returnCartRepository.findById(returnCartId).isPresent() && productRepository.findById(productId).isPresent() &&
                orderRepository.findById(returnCart.getCustomerOrder().getId()).isPresent()) {
            for (int i = 0; i < order.getProductList().size(); i++) {
                if (order.getProductList().get(i) == product) {
                    if (productDto.getAmountOfOrderedProducts() <= order.getProductList().get(i).getAmountOfOrderedProducts()) {
                        List<ReturnCart> returnCartsInProduct = product.getReturnCartList();
                        returnCartsInProduct.add(returnCart);
                        product.setReturnCartList(returnCartsInProduct);
                        productRepository.save(product);

                        List<Product> productList = returnCart.getReturnProductList();
                        product.setAmountOfReturningProducts(productDto.getAmountOfReturningProducts());
                        for (int j = 0; j < productList.size(); j++) {
                            if(product.getProductName().equalsIgnoreCase(productList.get(i).getProductName())) {
                                throw new RecordNotFoundException("Product is already in return cart!");
                            }
                        }
                        productList.add(product);
                        product.setAmountOfReturningProducts(productDto.getAmountOfReturningProducts());
                        returnCart.setReturnProductList(productList);
                        returnCart.setTotalPrice(returnCart.getTotalPrice() + product.getSellingPrice() * productDto.getAmountOfReturningProducts());

                        order.getProductList().get(i).setAmountOfOrderedProducts(order.getProductList().get(i).getAmountOfOrderedProducts() - productDto.getAmountOfReturningProducts());
                        order.setTotalPrice(order.getTotalPrice() - product.getSellingPrice() * productDto.getAmountOfReturningProducts());

                        orderRepository.save(order);
                        returnCartRepository.save(returnCart);
                        return TransferModelToDto.transferToReturnCartDto(returnCart);
                    } else {
                        throw new RecordNotFoundException("You cannot return more products then you ordered");
                    }
                    }

            }
            throw new RecordNotFoundException("Couldn't find the product you want to return");


        }
        throw new RecordNotFoundException("Couldn't find return cart");
    }




}
