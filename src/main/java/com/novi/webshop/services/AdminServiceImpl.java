package com.novi.webshop.services;

import com.novi.webshop.dto.UserDto;
import com.novi.webshop.dto.UserEmployeeDto;
import com.novi.webshop.dto.UserEmployeeInputDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.Admin;
import com.novi.webshop.model.Employee;
import com.novi.webshop.model.Orders;
import com.novi.webshop.repository.AdminRepository;
import com.novi.webshop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, EmployeeRepository employeeRepository) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<UserEmployeeDto> divideShoppingCartOrdersToEmployees(long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow();
        List<Employee> employeeList = employeeRepository.findAll();
        List<UserEmployeeDto> employeeDtoList = new ArrayList<>();
//        Admin admin = adminList.get(0);
//        if(adminList.size() == 1) {
//            admin = adminList.get(0);
//        } else {
//            throw new RecordNotFoundException("You either don't have an admin or you have too many admins!" +
//                    "There can only be one admin!");
//        }
        List<Orders> notProcessedOrders = admin.getAllNotProcessedOrders();
        int amountOfNotProcessedOrders = notProcessedOrders.size();
        int amountOfEmployees = employeeList.size();
        int ordersPerEmployee = (amountOfNotProcessedOrders / amountOfEmployees) + 1;
        for (int i = 0; i < amountOfEmployees; i++) {
            int shoppingCartIndex = 0;
//            employeeList.get(i).setShoppingCartList(new ArrayList<>());
            List<Orders> orderList = employeeList.get(i).getOrderList();
            while (orderList.size() < ordersPerEmployee) {

            for (int j = shoppingCartIndex; j < amountOfNotProcessedOrders; j++) {
                orderList.add(notProcessedOrders.get(j));
                    shoppingCartIndex = j;
                }
                employeeList.get(i).setOrderList(orderList);
                employeeRepository.save(employeeList.get(i));
                employeeDtoList.add(TransferModelToDto.transferToUserEmployeeDto(employeeList.get(i)));
            }
        }
        return employeeDtoList;
    }

    @Override
    public UserEmployeeDto createEmployeeAccount(UserEmployeeInputDto userEmployeeInputDto) {
        Employee employee = new Employee();
        employee.setEmailAddress(userEmployeeInputDto.getEmailAddress());
        employee.setUsername(userEmployeeInputDto.getUsername());
        employee.setPassword(userEmployeeInputDto.getPassword());
        employee.setFirstName(userEmployeeInputDto.getFirstName());
        employee.setLastName(userEmployeeInputDto.getLastName());
        Employee savedEmployee = employeeRepository.save(employee);
        return TransferModelToDto.transferToUserEmployeeDto(savedEmployee);
    }

    @Override
    public Admin createAdmin(UserDto userDto) {
        Admin admin = new Admin();
        admin.setUsername(userDto.getUsername());
        adminRepository.save(admin);
        return admin;
    }


}
