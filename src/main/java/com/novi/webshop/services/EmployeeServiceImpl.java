package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.UserEmployeeDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Employee;
import com.novi.webshop.model.Orders;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.EmployeeRepository;
import com.novi.webshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OrderServiceImpl orderService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, OrderServiceImpl orderService, OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<UserEmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<UserEmployeeDto> employeeDtoList = new ArrayList<>();
        for(int i = 0; i < employeeList.size(); i++) {
            employeeDtoList.add(TransferModelToDto.transferToUserEmployeeDto(employeeList.get(i)));
        }
        return employeeDtoList;
    }
    @Override
    public UserEmployeeDto getEmployeeById(Long employeeId) {
        if(employeeRepository.findById(employeeId).isPresent()) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow();
            return TransferModelToDto.transferToUserEmployeeDto(employee);
        } else {
            throw new RecordNotFoundException("Employee doesn't exist");
        }
    }

    @Override
    public UserEmployeeDto confirmIfOrderIsPaid(Long employeeId, Long orderId, boolean isPaid) {
        if(employeeRepository.findById(employeeId).isPresent()) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow();
            List<Orders> orders = employee.getOrderList();
            for (int i = 0; i < orders.size(); i++) {
                if(Objects.equals(orders.get(i).getId(), orderId)) {
                    orders.get(i).setPaid(isPaid);
                    employee.setOrderList(orders);
                    Employee savedEmployee = employeeRepository.save(employee);
                    if(!orders.get(i).isPaid()) {
                        throw new RecordNotFoundException("Wait till the order is paid! skip this order for now!");
                    }
                    return TransferModelToDto.transferToUserEmployeeDto(savedEmployee);
                }
            } throw new RecordNotFoundException("Couldn't find the order in this employee");
        } else {
            throw new RecordNotFoundException("Couldn't find employee");
        }
    }

    @Override
    public UserEmployeeDto processOrder(Long employeeId, Long orderId) {
        if(employeeRepository.findById(employeeId).isPresent()) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow();
            List<Orders> orders = employee.getOrderList();
            List<Orders> finishedOrders = employee.getFinishedOrders();
            for (int i = 0; i < orders.size(); i++) {
                if(orders.get(i).getId().equals(orderId)) {
                    orders.get(i).setProcessed(true);
                    finishedOrders.add(orders.get(i));
                    orders.remove(i);
                    employee.setOrderList(orders);
                    employee.setFinishedOrders(finishedOrders);
                    Employee savedEmployee = employeeRepository.save(employee);
                    return TransferModelToDto.transferToUserEmployeeDto(savedEmployee);
                }
            } throw new RecordNotFoundException("Couldn't find the order in this employee");
        } else {
            throw new RecordNotFoundException("Couldn't find employee");
        }
    }


    @Override
    public UserEmployeeDto addOrderToEmployeeList(Long employeeId, Long orderId) {
        if (employeeRepository.findById(employeeId).isPresent() && orderRepository.findById(orderId).isPresent()) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow();
            Orders order = orderRepository.findById(orderId).orElseThrow();
            order.setEmployeeOrderList(employee);
            orderRepository.save(order);
            List<Orders> orderList = employee.getOrderList();
            orderList.add(order);
            employee.setOrderList(orderList);
            employeeRepository.save(employee);
            return TransferModelToDto.transferToUserEmployeeDto(employee);
        } else {
            throw new RecordNotFoundException("Employee or order doesn't exist");
        }
    }
}
