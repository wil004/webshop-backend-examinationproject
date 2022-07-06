package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.dto.ReturnsDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.Orders;
import com.novi.webshop.model.Product;
import com.novi.webshop.model.QuantityAndProduct;
import com.novi.webshop.model.Returns;
import com.novi.webshop.repository.OrderRepository;
import com.novi.webshop.repository.ProductRepository;
import com.novi.webshop.repository.QuantityAndProductRepository;
import com.novi.webshop.repository.ReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReturnsServiceImpl implements ReturnsService {

    private final ReturnsRepository returnsRepository;
    private final ProductRepository productRepository;
    private final QuantityAndProductRepository quantityAndProductRepository;
    private final OrderRepository orderRepository;

    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public ReturnsServiceImpl(ReturnsRepository returnsRepository, ProductRepository productRepository, QuantityAndProductRepository quantityAndProductRepository, OrderRepository orderRepository, OrderServiceImpl orderServiceImpl) {
        this.returnsRepository = returnsRepository;
        this.productRepository = productRepository;
        this.quantityAndProductRepository = quantityAndProductRepository;
        this.orderRepository = orderRepository;
        this.orderServiceImpl = orderServiceImpl;
    }

    @Override
    public List<ReturnsDto> getAllReturns() {
        List<Returns> returnsList = returnsRepository.findAll();
        List<ReturnsDto> returnsDtoList = new ArrayList<>();
        for (int i = 0; i < returnsList.size(); i++) {
            returnsDtoList.add(TransferModelToDto.transferToReturnsDto(returnsList.get(i)));
        }
        return returnsDtoList;
    }

    @Override
    public List<ReturnsDto> getProcessedOrNotProcessedReturns(boolean processedOrNotProcessed) {
        List<Returns> allReturnsList = returnsRepository.findAll();
        List<Returns> allReturnsCartsWithProcessedStatuses = new ArrayList<>();
            for (int i = 0; i < allReturnsList.size(); i++) {
                if (processedOrNotProcessed) {
                    if (allReturnsList.get(i).isProcessed()) {
                        allReturnsCartsWithProcessedStatuses.add(allReturnsList.get(i));
                    }
                } else {
                    if (!allReturnsList.get(i).isProcessed()) {
                        allReturnsCartsWithProcessedStatuses.add(allReturnsList.get(i));
                    }
                }
            }

            List<ReturnsDto> returnsDtoList = new ArrayList<>();
            for (int i = 0; i < allReturnsCartsWithProcessedStatuses.size(); i++) {
                returnsDtoList.add(TransferModelToDto.transferToReturnsDto(allReturnsCartsWithProcessedStatuses.get(i)));
            }

            return returnsDtoList;
        }

    @Override
    public List<ReturnsDto> getReturnByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber) {
        List<OrderDto> orderDtoList = orderServiceImpl.getOrdersByNameAndAddress(firstName, lastName, zipcode, houseNumber);
        List<Orders> orderList = new ArrayList<>();
        List<ReturnsDto> returnsDtoList = new ArrayList<>();
        for (int i = 0; i < orderDtoList.size(); i++) {
            if(orderRepository.findById(orderDtoList.get(i).getId()).isPresent()) {
                orderList.add(orderRepository.findById(orderDtoList.get(i).getId()).orElseThrow());
            }
            for (int j = 0; j < orderList.get(i).getReturnList().size(); j++) {
                returnsDtoList.add(TransferModelToDto.transferToReturnsDto(orderList.get(i).getReturnList().get(j)));
            }
        }
        return returnsDtoList;
    }

    @Override
    public List<ReturnsDto> getReturnByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber, String additionalHouseNumber) {
        List<OrderDto> orderDtoList = orderServiceImpl.getOrdersByNameAndAddress(firstName, lastName, zipcode, houseNumber, additionalHouseNumber);
        List<Orders> orderList = new ArrayList<>();
        List<ReturnsDto> returnsDtoList = new ArrayList<>();
        for (int i = 0; i < orderDtoList.size(); i++) {
            if(orderRepository.findById(orderDtoList.get(i).getId()).isPresent()) {
                orderList.add(orderRepository.findById(orderDtoList.get(i).getId()).orElseThrow());
            }
            for (int j = 0; j < orderList.get(i).getReturnList().size(); j++) {
                returnsDtoList.add(TransferModelToDto.transferToReturnsDto(orderList.get(i).getReturnList().get(j)));
            }
        }
        return returnsDtoList;
    }

    @Override
    public ReturnsDto changeProcessedStatus(Long id, boolean processed) {
        if(returnsRepository.findById(id).isPresent()) {
            Returns returns = returnsRepository.findById(id).orElseThrow();
            returns.setProcessed(processed);
            Returns savedReturns = returnsRepository.save(returns);
            return TransferModelToDto.transferToReturnsDto(savedReturns);
        }
        else {
            throw new RecordNotFoundException("Couldn't find return-cart");
        }
    }

    @Override
    public ReturnsDto createReturnProducts(Long orderId) {
        Returns returns = new Returns();
        Orders order = orderRepository.findById(orderId).orElseThrow();
        if (orderRepository.findById(orderId).isPresent()) {
            returns.setCustomerOrder(order);
            if (within30DaysReturnTime(returns.getCustomerOrder())) {
                if(returns.getCustomerOrder().isProcessed()) {
                    Returns savedReturns = returnsRepository.save(returns);
                    return TransferModelToDto.transferToReturnsDto(savedReturns);
                } else {
                    throw new RecordNotFoundException("The order is not processed yet wait till you receive your products to return!");
                }
            } else {
                throw new RecordNotFoundException("Returning time has been expired, you can't return products anymore!");
            }
        } else {
            throw new RecordNotFoundException("Couldn't connect a shopping cart to the return cart");
        }
    }


    private boolean within30DaysReturnTime(Orders order) {
        long maximumReturnTime = order.getOrderDateInMilliSeconds() + 1000L * 60 * 60 * 24 * 30;
        long currentTime = System.currentTimeMillis();
        if (currentTime > maximumReturnTime) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public ReturnsDto connectProductWithReturn(Long returnCartId, Long productId, ProductDto productDto) {
        Returns returns = returnsRepository.findById(returnCartId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        Orders order = returns.getCustomerOrder();

        if (returnsRepository.findById(returnCartId).isPresent() && productRepository.findById(productId).isPresent() &&
                orderRepository.findById(returns.getCustomerOrder().getId()).isPresent()) {
            within30DaysReturnTime(order);
            for (int i = 0; i < order.getQuantityAndProductList().size(); i++) {
                if (Objects.equals(order.getQuantityAndProductList().get(i).getProduct().getProductName(), product.getProductName())) {

                    int returningProductsAmount = productDto.getAmountOfReturningProducts();
                    if (returningProductsAmount <= order.getQuantityAndProductList().get(i).getAmountOfProducts()) {
                        List<QuantityAndProduct> productList = returns.getQuantityAndProductList();
                        QuantityAndProduct quantityAndProduct = order.getQuantityAndProductList().get(i);
                        quantityAndProduct.setReturns(returns);
                        quantityAndProduct.setAmountOfReturningProducts(returningProductsAmount + order.getQuantityAndProductList().get(i).getAmountOfReturningProducts());
                        productList.add(quantityAndProduct);
                        returns.setQuantityAndProductList(productList);
                        double totalPrice = 0;
                        for(int j = 0; j < productList.size();j++) {
                            totalPrice = totalPrice + productList.get(j).getProduct().getPrice() * productList.get(j).getAmountOfReturningProducts();
                        }
                        returns.setTotalPrice(totalPrice);
                        order.getQuantityAndProductList().get(i).setAmountOfProducts(quantityAndProduct.getAmountOfProducts() - productDto.getAmountOfReturningProducts());
                        order.setTotalPrice(order.getTotalPrice() - totalPrice);
                        quantityAndProductRepository.save(quantityAndProduct);
                        orderRepository.save(order);
                        returnsRepository.save(returns);
                        return TransferModelToDto.transferToReturnsDto(returns);
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
