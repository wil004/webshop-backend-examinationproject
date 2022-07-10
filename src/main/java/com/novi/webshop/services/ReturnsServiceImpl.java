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
    private final TransferModelToDto transferModelToDto;
    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public ReturnsServiceImpl(ReturnsRepository returnsRepository, ProductRepository productRepository, QuantityAndProductRepository quantityAndProductRepository, OrderRepository orderRepository, TransferModelToDto transferModelToDto, OrderServiceImpl orderServiceImpl) {
        this.returnsRepository = returnsRepository;
        this.productRepository = productRepository;
        this.quantityAndProductRepository = quantityAndProductRepository;
        this.orderRepository = orderRepository;
        this.transferModelToDto = transferModelToDto;
        this.orderServiceImpl = orderServiceImpl;
    }

    @Override
    public List<ReturnsDto> getAllReturns() {
        List<Returns> returnsList = returnsRepository.findAll();
        List<ReturnsDto> returnsDtoList = new ArrayList<>();
        for (int i = 0; i < returnsList.size(); i++) {
            returnsDtoList.add(transferModelToDto.transferToReturnsDto(returnsList.get(i)));
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
                returnsDtoList.add(transferModelToDto.transferToReturnsDto(allReturnsCartsWithProcessedStatuses.get(i)));
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
                returnsDtoList.add(transferModelToDto.transferToReturnsDto(orderList.get(i).getReturnList().get(j)));
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
                returnsDtoList.add(transferModelToDto.transferToReturnsDto(orderList.get(i).getReturnList().get(j)));
            }
        }
        return returnsDtoList;
    }

    @Override
    public Object changeProcessedStatus(Long id, boolean processed) {
      return orderServiceImpl.changeProcessedStatus(id, processed, "return");
    }

    @Override
    public ReturnsDto createReturnProducts(Long orderId, ReturnsDto returnsDto) {
        Returns returns = new Returns();
        returns.setBankAccountForReturn(returnsDto.getBankAccountForReturn());
        Orders order = orderRepository.findById(orderId).orElseThrow();
        if (orderRepository.findById(orderId).isPresent()) {
            returns.setCustomerOrder(order);
            if (within30DaysReturnTime(returns.getCustomerOrder())) {
                if(returns.getCustomerOrder().isProcessed()) {
                    Returns savedReturns = returnsRepository.save(returns);
                    return transferModelToDto.transferToReturnsDto(savedReturns);
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
    public ReturnsDto connectProductWithReturn(Long returnsId, Long productId, ProductDto productDto) {
        Returns returns = returnsRepository.findById(returnsId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        Orders order = returns.getCustomerOrder();

        if (returnsRepository.findById(returnsId).isPresent() && productRepository.findById(productId).isPresent() &&
                orderRepository.findById(returns.getCustomerOrder().getId()).isPresent()) {
            within30DaysReturnTime(order);

            int returningProductsAmount = 0;

            for (int i = 0; i < order.getQuantityAndProductList().size(); i++) {
                if (Objects.equals(order.getQuantityAndProductList().get(i).getProduct().getProductName(), product.getProductName())) {
                    returningProductsAmount = order.getQuantityAndProductList().get(i).getAmountOfReturningProducts() + productDto.getAmountOfReturningProducts();
                    if (returningProductsAmount <= order.getQuantityAndProductList().get(i).getAmountOfProducts()) {
                        List<QuantityAndProduct> productList = returns.getQuantityAndProductList();
                        QuantityAndProduct quantityAndProduct = order.getQuantityAndProductList().get(i);
                        quantityAndProduct.setReturns(returns);
                        quantityAndProduct.setAmountOfReturningProducts(returningProductsAmount);

                        if(productList.contains(quantityAndProduct)) {
                            boolean foundObject = false;
                            for (int j = 0; j < productList.size(); j++) {
                                for (int k = 0; k < productList.size(); k++) {
                                    if (productList.get(j).equals(productList.get(k))) {
                                        productList.set(j, quantityAndProduct);
                                        foundObject = true;
                                        break;
                                    }
                                }
                                if(foundObject) {
                                    break;
                                }
                            }
                        } else {
                            productList.add(quantityAndProduct);
                        }

                        returns.setQuantityAndProductList(productList);
                        double totalPrice = 0;
                        for(int j = 0; j < productList.size();j++) {
                                totalPrice = totalPrice + productList.get(j).getProduct().getPrice() * productList.get(j).getAmountOfReturningProducts();
                        }
                        returns.setTotalPrice(totalPrice);
                        quantityAndProductRepository.save(quantityAndProduct);
                        orderRepository.save(order);
                        returnsRepository.save(returns);
                        return transferModelToDto.transferToReturnsDto(returns);
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
