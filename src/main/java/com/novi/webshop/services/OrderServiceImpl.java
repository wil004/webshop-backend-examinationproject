package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.dto.ShoppingCartDto;
import com.novi.webshop.helpers.TransferDtoToModel;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.*;
import com.novi.webshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;
    private final QuantityAndProductRepository quantityAndProductRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, QuantityAndProductRepository quantityAndProductRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.quantityAndProductRepository = quantityAndProductRepository;

    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, EmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.employeeRepository = employeeRepository;

    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Orders> orderList = orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            orderDtoList.add(TransferModelToDto.transferToOrderDto(orderList.get(i)));;
        }

        return orderDtoList;
    }

    @Override
    public OrderDto orderIsPaid(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow();
        if(orderRepository.findById(orderId).isPresent()) {
            order.setPaid(true);
        Orders savedOrder = orderRepository.save(order);
        return TransferModelToDto.transferToOrderDto(savedOrder);
        } else {
            throw new RecordNotFoundException("Order doesn't exist");
        }
    }

    @Override
    public OrderDto changeProcessedStatus(Long orderId, boolean processed) {
        if(orderRepository.findById(orderId).isPresent()) {
            Orders order = orderRepository.findById(orderId).orElseThrow();
            if(order.isPaid()) {

                // deleteOrdeInEmployee makes sure the order will not be prepared twice!

                deleteOrderInEmployee(orderId);
                order.setProcessed(processed);
                Orders savedOrder = orderRepository.save(order);
                return TransferModelToDto.transferToOrderDto(savedOrder);
            } else {
                throw new RecordNotFoundException("Please check if the order is paid first!");
            }
        }
        else {
            throw new RecordNotFoundException("Couldn't find order");
        }
    }

    private void deleteOrderInEmployee(Long orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            Orders order = orderRepository.findById(orderId).orElseThrow();
            List<Employee> employeeList = employeeRepository.findAll();
            for (int i = 0; i < employeeList.size(); i++) {
                for (int j = 0; j < employeeList.get(i).getOrderList().size(); j++) {
                    if (order.equals(employeeList.get(i).getOrderList().get(j))) {
                        employeeList.get(i).getOrderList().remove(j);
                        employeeRepository.save(employeeList.get(i));
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
            orderDtoList.add(TransferModelToDto.transferToOrderDto(allOrdersWithProcessedStatus.get(i)));
        }
        return orderDtoList;
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Orders order = orderRepository.findById(id).orElseThrow();
        if(orderRepository.findById(id).isPresent()) {
            System.out.println(order);
            return TransferModelToDto.transferToOrderDto(order);
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
            orderDtoList.add(TransferModelToDto.transferToOrderDto(foundOrders.get(i)));
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
            orderDtoList.add(TransferModelToDto.transferToOrderDto(foundOrders.get(i)));
        }
        return orderDtoList;
    }


    @Override
    public OrderDto createOrderFromCustomer(Long customerId) {
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
            quantityAndProductRepository.saveAll(quantityAndProductList);
            Orders savedOrder = orderRepository.save(order);
            customerRepository.save(customer);

        if(customer.getShoppingCart().getProductList().size() > 0) {
            for (int i = 0; i < customer.getShoppingCart().getProductList().size(); i++) {
                if (productRepository.findById(customer.getShoppingCart().getProductList().get(i).getId()).isPresent()) {
                    productList.add(productRepository.findById(customer.getShoppingCart().getProductList().get(i).getId()).orElseThrow());
                }
            }
            order.setProductList(productList);
            List<Orders> orderHistory = customer.getOrderHistory();

            customer.getShoppingCart().setProductList(null);
            customer.getShoppingCart().setTotalPrice(0);
            orderHistory.add(order);
            customer.setOrderHistory(orderHistory);
            customerRepository.save(customer);
            Orders savedOrder = orderRepository.save(order);

            OrderDto orderDto = TransferModelToDto.transferToOrderDto(savedOrder);
            orderDto.setCustomerDto(TransferModelToDto.transferToCustomerDto(customer));
            return orderDto;
        } else {
            throw new RecordNotFoundException("Shoppingcart is empty");
        }
    }

    @Override
    public OrderDto createOrderFromGuestCustomer(Long customerId, ShoppingCartDto shoppingCartDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        ShoppingCart shoppingCart = TransferDtoToModel.transferToShoppingCart(shoppingCartDto);
        List<QuantityAndProduct> quantityAndProductList = new ArrayList<>();


        Orders order = new Orders();
        order.setProcessed(false);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderDateInMilliSeconds(System.currentTimeMillis());
        order.setCustomer(customer);
        if(shoppingCartDto.getProductList().size() > 0) {
            for (int i = 0; i < shoppingCartDto.getProductList().size(); i++) {
                QuantityAndProduct quantityAndProduct = new QuantityAndProduct();
                quantityAndProduct.setProduct(customer.getShoppingCart().getQuantityAndProductList().get(i).getProduct());
                quantityAndProduct.setAmountOfProducts(customer.getShoppingCart().getQuantityAndProductList().get(i).getAmountOfProducts());
                quantityAndProductList.add(quantityAndProduct);
                quantityAndProductList.get(i).setOrder(order);
                    if(shoppingCartDto.getProductList().get(i).getAmountOfProducts() <= 0) {
                        throw new RecordNotFoundException("No valid amount of products!");
                }
            }
        }
        if(quantityAndProductList.size() > 0) {
            order.setQuantityAndProductList(quantityAndProductList);
                if (productRepository.findById(shoppingCartDto.getProductList().get(i).getId()).isPresent()) {
                    productList.add(productRepository.findById(shoppingCartDto.getProductList().get(i).getId()).orElseThrow());
                }
            }
        }
        if(productList.size() > 0) {
            order.setProductList(productList);
        } else {
            throw new RecordNotFoundException("No products in shoppingcart!");
        }
        List<Orders> orderHistory = customer.getOrderHistory();
        orderHistory.add(order);
        customer.setOrderHistory(orderHistory);
        quantityAndProductRepository.saveAll(quantityAndProductList);
        Orders savedOrder = orderRepository.save(order);
        customerRepository.save(customer);
        OrderDto orderDto = TransferModelToDto.transferToOrderDto(savedOrder);
        orderDto.setCustomerDto(TransferModelToDto.transferToCustomerDto(customer));
        return orderDto;
    }


    private void saveQuantityAndProduct(Orders order, QuantityAndProduct quantityAndProduct) {
        quantityAndProduct.setOrder(order);
        quantityAndProductRepository.save(quantityAndProduct);
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
