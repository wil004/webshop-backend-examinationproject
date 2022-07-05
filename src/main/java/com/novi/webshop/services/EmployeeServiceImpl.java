package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.OrderDto;
import com.novi.webshop.dto.ReturnsDto;
import com.novi.webshop.dto.UserEmployeeDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.Employee;
import com.novi.webshop.model.Orders;
import com.novi.webshop.model.Returns;
import com.novi.webshop.repository.EmployeeRepository;
import com.novi.webshop.repository.OrderRepository;
import com.novi.webshop.repository.ReturnsRepository;
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
    private final ReturnsService returnsService;
    private final ReturnsRepository returnsRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, OrderServiceImpl orderService, OrderRepository orderRepository, ReturnsService returnsService, ReturnsRepository returnsRepository) {
        this.employeeRepository = employeeRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.returnsService = returnsService;
        this.returnsRepository = returnsRepository;
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
            List<Orders> orders = new ArrayList<>(employee.getOrderList());
            List<Orders> finishedOrders = new ArrayList<>(employee.getFinishedOrders());
            for (int i = 0; i < orders.size(); i++) {
                if(orders.get(i).getId().equals(orderId)) {
                    Orders order = orders.get(i);
                    order.setEmployeeOrderList(employee);
                    orders.get(i).setProcessed(true);
                    finishedOrders.add(orders.get(i));
                    orders.remove(i);
                    employee.setOrderList(orders);
                    employee.setFinishedOrders(finishedOrders);
                    order.setEmployeeFinishedOrderList(employee);
                    order.setEmployeeOrderList(null);
                    orderRepository.save(order);
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

    @Override
    public List<UserEmployeeDto> divideOrdersOverEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<OrderDto> orderList = orderService.getAllProcessedOrNotProcessedOrders(false);
        int ordersPerEmployee = 0;
        if(orderList.size() > employeeList.size()) {
           ordersPerEmployee = orderList.size() / employeeList.size() + 1;
        } else {
            ordersPerEmployee = 1;
        }
        int jIndex= 0;
        for(int i = 0; i < employeeList.size(); i++) {
                for (int j = jIndex; j < orderList.size(); j++) {
                    if(employeeList.get(i).getOrderList().size() < ordersPerEmployee) {
                        addOrderToEmployeeList(employeeList.get(i).getId(), orderList.get(j).getId());
                    } else {
                        jIndex = j;
                        break;
                    }
            }
        }
        List<UserEmployeeDto> employeeDtoList = new ArrayList<>();
        for(int i = 0; i < employeeList.size(); i++) {
            employeeDtoList.add(TransferModelToDto.transferToUserEmployeeDto(employeeList.get(i)));
        }
        return employeeDtoList;
    }

    @Override
    public UserEmployeeDto addReturnsToEmployeeList(Long employeeId, Long returnsId) {
        if (employeeRepository.findById(employeeId).isPresent() && returnsRepository.findById(returnsId).isPresent()) {
            Employee employee = employeeRepository.findById(employeeId).orElseThrow();
            Returns returns = returnsRepository.findById(returnsId).orElseThrow();
            returns.setEmployeeReturnCartList(employee);
            returnsRepository.save(returns);
            List<Returns> returnsList = employee.getReturnCartList();
            returnsList.add(returns);
            employee.setReturnCartList(returnsList);
            employeeRepository.save(employee);
            return TransferModelToDto.transferToUserEmployeeDto(employee);
        } else {
            throw new RecordNotFoundException("Employee or returns doesn't exist");
        }
    }

    @Override
    public List<UserEmployeeDto> divideReturnsOverEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<ReturnsDto> returnsDtoList = returnsService.getProcessedOrNotProcessedReturns(false);
        int returnsPerEmployee = 0;
        if(returnsDtoList.size() > employeeList.size()) {
            returnsPerEmployee = returnsDtoList.size() / employeeList.size() + 1;
        } else {
            returnsPerEmployee = 1;
        }
        int jIndex= 0;
        for(int i = 0; i < employeeList.size(); i++) {
            for (int j = jIndex; j < returnsDtoList.size(); j++) {
                if(employeeList.get(i).getReturnCartList().size() < returnsPerEmployee) {
                    addOrderToEmployeeList(employeeList.get(i).getId(), returnsDtoList.get(j).getId());
                } else {
                    jIndex = j;
                    break;
                }
            }
        }
        List<UserEmployeeDto> employeeDtoList = new ArrayList<>();
        for(int i = 0; i < employeeList.size(); i++) {
            employeeDtoList.add(TransferModelToDto.transferToUserEmployeeDto(employeeList.get(i)));
        }
        return employeeDtoList;
    }
}
