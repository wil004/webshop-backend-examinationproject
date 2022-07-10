package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.*;
import com.novi.webshop.repository.*;
import com.novi.webshop.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ReturnsRepository returnsRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final QuantityAndProductRepository quantityAndProductRepository;
    private final ProductRepository productRepository;
    private final UserServiceImpl userServiceImpl;
    private final TransferModelToDto transferModelToDto;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ReturnsRepository returnsRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, QuantityAndProductRepository quantityAndProductRepository, ProductRepository productRepository, UserServiceImpl userServiceImpl, TransferModelToDto transferModelToDto) {
        this.orderRepository = orderRepository;
        this.returnsRepository = returnsRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.quantityAndProductRepository = quantityAndProductRepository;
        this.productRepository = productRepository;
        this.userServiceImpl = userServiceImpl;
        this.transferModelToDto = transferModelToDto;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Orders> orderList = orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            orderDtoList.add(transferModelToDto.transferToOrderDto(orderList.get(i)));;
        }

        return orderDtoList;
    }

    @Override
    public OrderDto orderIsPaid(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow();
        if(orderRepository.findById(orderId).isPresent()) {
            order.setPaid(true);
        Orders savedOrder = orderRepository.save(order);
        return transferModelToDto.transferToOrderDto(savedOrder);
        } else {
            throw new RecordNotFoundException("Order doesn't exist");
        }
    }

    @Override
    public Object changeProcessedStatus(Long id, boolean processed, String orderOrReturn) {
        if(orderOrReturn.equalsIgnoreCase("order")) {
            if (orderRepository.findById(id).isPresent()) {
                Orders order = orderRepository.findById(id).orElseThrow();
                if (order.isPaid()) {
                    if(employeeRepository.findAll().get(0) != null) {
                        deleteOrderOrReturnInEmployee(id, orderOrReturn);
                    }
                    order.setProcessed(processed);
                    Orders savedOrder = orderRepository.save(order);
                    return transferModelToDto.transferToOrderDto(savedOrder);
                } else {
                    throw new RecordNotFoundException("Please check if the order is paid first!");
                }
            } else {
                throw new RecordNotFoundException("Couldn't find order");
            }
        } else if(returnsRepository.findById(id).isPresent() && orderOrReturn.equalsIgnoreCase("return")) {
            Returns returns = returnsRepository.findById(id).orElseThrow();
            if(employeeRepository.findAll().get(0) != null) {
                deleteOrderOrReturnInEmployee(id, orderOrReturn);
            }
            returns.setProcessed(processed);
            Returns savedReturn = returnsRepository.save(returns);
            return transferModelToDto.transferToReturnsDto(savedReturn);
        }
        else {
            throw new RecordNotFoundException("Couldn't find return");
        }
    }

    private void deleteOrderOrReturnInEmployee(Long id, String orderOrReturn) {
        List<Employee> employeeList = employeeRepository.findAll();
        if (orderRepository.findById(id).isPresent() && orderOrReturn.equalsIgnoreCase("order")) {
            Orders order = orderRepository.findById(id).orElseThrow();
            for (int i = 0; i < employeeList.size(); i++) {
                for (int j = 0; j < employeeList.get(i).getOrderList().size(); j++) {
                    if (order.equals(employeeList.get(i).getOrderList().get(j))) {
                        employeeList.get(i).getOrderList().remove(j);
                        employeeRepository.save(employeeList.get(i));
                        order.setEmployeeOrderList(null);
                        orderRepository.save(order);
                        break;
                    }
                }
            }
        } else if(returnsRepository.findById(id).isPresent() && orderOrReturn.equalsIgnoreCase("return")) {
            Returns returns = returnsRepository.findById(id).orElseThrow();
            for (int i = 0; i < employeeList.size(); i++) {
                for (int j = 0; j < employeeList.get(i).getReturnsList().size(); j++) {
                    if (returns.equals(employeeList.get(i).getReturnsList().get(j))) {
                        employeeList.get(i).getReturnsList().remove(j);
                        employeeRepository.save(employeeList.get(i));
                        returns.setEmployeeReturnsList(null);
                        returnsRepository.save(returns);
                        break;
                    }
                }
            }
        }
    }


    @Override
    public List<OrderDto> getAllProcessedOrNotProcessedOrders(boolean processedOrNotProcessed) {
        List<Orders> allOrdersList = orderRepository.findAll();
        List<Orders> allOrdersWithProcessedStatus = new ArrayList<>();

        for (int i = 0; i < allOrdersList.size(); i++) {
            if(processedOrNotProcessed) {
                if (allOrdersList.get(i).isProcessed()) {
                    allOrdersWithProcessedStatus.add(allOrdersList.get(i));
                }
            } else {
                if (!allOrdersList.get(i).isProcessed()) {
                    long orderTime30days = allOrdersList.get(i).getOrderDateInMilliSeconds() + 1000L * 60 * 60 * 24 * 30;
                    long currentTime = System.currentTimeMillis();
                    if(currentTime > orderTime30days) {
                        orderRepository.deleteById(allOrdersList.get(i).getId());
                    } else {
                        allOrdersWithProcessedStatus.add(allOrdersList.get(i));
                    }
                }
            }
        }
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (int i = 0; i < allOrdersWithProcessedStatus.size(); i++) {
            orderDtoList.add(transferModelToDto.transferToOrderDto(allOrdersWithProcessedStatus.get(i)));
        }
        return orderDtoList;
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Orders order = orderRepository.findById(id).orElseThrow();
        if(orderRepository.findById(id).isPresent()) {
            System.out.println(order);
            return transferModelToDto.transferToOrderDto(order);
        } else {
            throw new RecordNotFoundException("Couldn't find order");
        }
    }

    @Override
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
            orderDtoList.add(transferModelToDto.transferToOrderDto(foundOrders.get(i)));
        }
        return orderDtoList;
    }


    @Override
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
            orderDtoList.add(transferModelToDto.transferToOrderDto(foundOrders.get(i)));
        }
        return orderDtoList;
    }


    @Override
    public OrderDto createOrder(Long customerId) {
            if (!Objects.equals(customerId, userServiceImpl.findIdFromUsername(JwtRequestFilter.getUsername()))
                    && !Objects.equals(userServiceImpl.findRoleFromUsername(JwtRequestFilter.getUsername()), "ADMIN")) {
                throw new RecordNotFoundException("Customer has only acces to his own data");
            }
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        List<QuantityAndProduct> quantityAndProductList = new ArrayList<>();
        Orders order = new Orders();
        order.setProcessed(false);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderDateInMilliSeconds(System.currentTimeMillis());
        order.setCustomer(customer);
        order.setTotalPrice(customer.getShoppingCart().getTotalPrice());

        if(customer.getShoppingCart().getQuantityAndProductList().size() > 0) {
            for(int i = 0; i < customer.getShoppingCart().getQuantityAndProductList().size(); i++) {
                QuantityAndProduct quantityAndProduct = new QuantityAndProduct();
                quantityAndProduct.setProduct(customer.getShoppingCart().getQuantityAndProductList().get(i).getProduct());
                quantityAndProduct.setAmountOfProducts(customer.getShoppingCart().getQuantityAndProductList().get(i).getAmountOfProducts());
                quantityAndProductList.add(quantityAndProduct);
                quantityAndProductList.get(i).setOrder(order);
            }

            order.setQuantityAndProductList(quantityAndProductList);
            List<Orders> orderHistory = customer.getOrderHistory();
            customer.getShoppingCart().setTotalPrice(0);
            orderHistory.add(order);
            customer.setOrderHistory(orderHistory);
            customer.getShoppingCart().setQuantityAndProductList(null);
            quantityAndProductRepository.saveAll(quantityAndProductList);
            Orders savedOrder = orderRepository.save(order);
            customerRepository.save(customer);
            OrderDto orderDto = transferModelToDto.transferToOrderDto(savedOrder);
            orderDto.setCustomerDto(transferModelToDto.transferToCustomerDto(customer));
            orderDto.getCustomerDto().setOrderHistoryDto(null);
            return orderDto;
        } else {
            throw new RecordNotFoundException("Shoppingcart is empty");
        }
    }


    // Code had to be duplicate because of the ShoppingCartDto parameter!
    @Override
    public OrderDto createOrderFromGuestCustomer(Long customerId, ShoppingCartDto shoppingCartDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        List<QuantityAndProduct> quantityAndProductList = new ArrayList<>();
        Orders order = new Orders();
        order.setProcessed(false);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderDateInMilliSeconds(System.currentTimeMillis());
        order.setCustomer(customer);

        double totalPrice = 0;
        if(shoppingCartDto.getProductList().size() > 0) {
            for (int i = 0; i < shoppingCartDto.getProductList().size(); i++) {
                if (productRepository.findById(shoppingCartDto.getProductList().get(i).getId()).isPresent()) {
                QuantityAndProduct quantityAndProduct = new QuantityAndProduct();
                    quantityAndProduct.setProduct(productRepository.findById(shoppingCartDto.getProductList().get(i).getId()).orElseThrow());
                    quantityAndProduct.setAmountOfProducts(shoppingCartDto.getProductList().get(i).getAmountOfProducts());
                    quantityAndProductList.add(quantityAndProduct);
                    quantityAndProductList.get(i).setOrder(order);
                    totalPrice = totalPrice + quantityAndProduct.getAmountOfProducts() * quantityAndProduct.getProduct().getPrice();
                } else {
                    throw new RecordNotFoundException("Product not found");
                }
                    if(shoppingCartDto.getProductList().get(i).getAmountOfProducts() <= 0) {
                        throw new RecordNotFoundException("No valid amount of products!");
                }
            }
        }



        order.setTotalPrice(totalPrice);

        if(shoppingCartDto.getProductList().size() > 0) {
            order.setQuantityAndProductList(quantityAndProductList);
        } else {
            throw new RecordNotFoundException("No products in shoppingcart!");
        }
        List<Orders> orderHistory = customer.getOrderHistory();
        orderHistory.add(order);
        customer.setOrderHistory(orderHistory);
        quantityAndProductRepository.saveAll(quantityAndProductList);
        Orders savedOrder = orderRepository.save(order);
        customerRepository.save(customer);
        OrderDto orderDto = transferModelToDto.transferToOrderDto(savedOrder);
        orderDto.setCustomerDto(transferModelToDto.transferToCustomerDto(customer));
        orderDto.getCustomerDto().setOrderHistoryDto(null);
        return orderDto;
    }

}
