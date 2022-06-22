package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.*;
import com.novi.webshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, AdminRepository adminRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDto> getAllOrders() {
        List<Orders> orderList = orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            orderDtoList.add(TransferModelToDto.transferToOrderDto(orderList.get(i)));
        }
        return orderDtoList;
    }

    public List<OrderDto> getProcessedOrNotProcessedOrders(boolean processedOrNotProcessed) {
        List<Orders> allOrdersList = orderRepository.findAll();
        List<Orders> allOrdersWithProcessedStatus = new ArrayList<>();

        for (int i = 0; i < allOrdersList.size(); i++) {
            if(processedOrNotProcessed) {
                if (allOrdersList.get(i).isProcessed()) {
                    allOrdersWithProcessedStatus.add(allOrdersList.get(i));
                    Admin admin = new Admin();
                    admin.setAllProcessedOrders(allOrdersWithProcessedStatus);
                    adminRepository.save(admin);
                }
            } else {
                if (!allOrdersList.get(i).isProcessed()) {
                    allOrdersWithProcessedStatus.add(allOrdersList.get(i));
                    Admin admin = new Admin();
                    admin.setAllNotProcessedOrders(allOrdersWithProcessedStatus);
                    adminRepository.save(admin);
                }
            }
        }
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (int i = 0; i < allOrdersWithProcessedStatus.size(); i++) {
            orderDtoList.add(TransferModelToDto.transferToOrderDto(allOrdersWithProcessedStatus.get(i)));
        }
        return orderDtoList;
    }

    public OrderDto getOrderById(Long id) {
        Orders order = orderRepository.findById(id).orElseThrow();
        if(orderRepository.findById(id).isPresent()) {
            return TransferModelToDto.transferToOrderDto(order);
        } else {
            throw new RecordNotFoundException("Couldn't find order");
        }
    }

    public List<OrderDto> getOrdersByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber) {
        List<Orders> allOrdersList = orderRepository.findAll();
        List<Orders> foundOrders = new ArrayList<>();
        for(int i = 0; i < allOrdersList.size(); i++) {
            if (allOrdersList.get(i).getCustomer().getFirstName().equalsIgnoreCase(firstName) && allOrdersList.get(i).getCustomer().getLastName().equalsIgnoreCase(lastName) &&
                    allOrdersList.get(i).getCustomer().getZipcode().equalsIgnoreCase(zipcode) && allOrdersList.get(i).getCustomer().getHouseNumber() == houseNumber &&
                    allOrdersList.get(i).getCustomer().getAdditionalToHouseNumber() == null) {
                foundOrders.add(allOrdersList.get(i));
            }
        }
        if(foundOrders.size() == 0) {
            throw new RecordNotFoundException("No order found!");
        }
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (int i = 0; i < foundOrders.size(); i++) {
            orderDtoList.add(TransferModelToDto.transferToOrderDto(foundOrders.get(i)));
        }
        return orderDtoList;
    }


    public List<OrderDto> getOrdersByNameAndAddress(String firstName, String lastName, String zipcode, int houseNumber, String additionalHouseNumber) {
        List<Orders> allOrdersList = orderRepository.findAll();
        List<Orders> foundOrders = new ArrayList<>();
        for(int i = 0; i < allOrdersList.size(); i++) {
            if (allOrdersList.get(i).getCustomer().getFirstName().equalsIgnoreCase(firstName) && allOrdersList.get(i).getCustomer().getLastName().equalsIgnoreCase(lastName) &&
                    allOrdersList.get(i).getCustomer().getZipcode().equalsIgnoreCase(zipcode) && allOrdersList.get(i).getCustomer().getHouseNumber() == houseNumber && allOrdersList.get(i).getCustomer().getAdditionalToHouseNumber().equalsIgnoreCase(additionalHouseNumber)) {
                foundOrders.add(allOrdersList.get(i));
            }
        }
        if(foundOrders.size() == 0) {
            throw new RecordNotFoundException("No order found!");
        }
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (int i = 0; i < foundOrders.size(); i++) {
            orderDtoList.add(TransferModelToDto.transferToOrderDto(foundOrders.get(i)));
        }
        return orderDtoList;
    }


    public OrderDto createOrderFromCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        List<Product> productList = new ArrayList<>();

        Orders order = new Orders();
        order.setProcessed(false);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderDateInMilliSeconds(System.currentTimeMillis());
        order.setCustomer(customer);
        order.setTotalPrice(customer.getShoppingCart().getTotalPrice());
        for(int i = 0; i < customer.getShoppingCart().getProductList().size(); i++) {
            if(productRepository.findById(customer.getShoppingCart().getProductList().get(i).getId()).isPresent()) {
                productList.add(productRepository.findById(customer.getShoppingCart().getProductList().get(i).getId()).orElseThrow());
            }
        }
        order.setProductList(productList);
        customer.getShoppingCart().setProductList(null);
        customer.getShoppingCart().setTotalPrice(0);
        customerRepository.save(customer);
        Orders savedOrder = orderRepository.save(order);
        OrderDto orderDto = TransferModelToDto.transferToOrderDto(savedOrder);
        orderDto.setCustomerDto(TransferModelToDto.transferToCustomerDto(customer));
        return orderDto;
    }

    public OrderDto createOrderFromGuestCustomer(Long customerId, ShoppingCartDto shoppingCartDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        List<Product> productList = new ArrayList<>();

        Orders order = new Orders();
        order.setProcessed(false);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderDateInMilliSeconds(System.currentTimeMillis());
        order.setCustomer(customer);
        for(int i = 0; i < shoppingCartDto.getProduct().size(); i++) {
            if(productRepository.findById(shoppingCartDto.getProduct().get(i).getId()).isPresent()) {
                productList.add(productRepository.findById(shoppingCartDto.getProduct().get(i).getId()).orElseThrow());
            }
        }
        order.setProductList(productList);
        Orders savedOrder = orderRepository.save(order);
        OrderDto orderDto = TransferModelToDto.transferToOrderDto(savedOrder);
        orderDto.setCustomerDto(TransferModelToDto.transferToCustomerDto(customer));
        return orderDto;
    }


    public OrderDto changeProcessedStatus(Long id, boolean processed) {
        if(orderRepository.findById(id).isPresent()) {
            Orders order = orderRepository.findById(id).orElseThrow();
            order.setProcessed(processed);
            Orders savedOrder = orderRepository.save(order);
            return TransferModelToDto.transferToOrderDto(savedOrder);
        }
        else {
            throw new RecordNotFoundException("Couldn't find order");
        }
    }


}
