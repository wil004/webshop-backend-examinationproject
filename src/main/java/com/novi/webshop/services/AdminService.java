package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.UserDto;
import com.novi.webshop.dto.UserEmployeeDto;
import com.novi.webshop.dto.UserEmployeeInputDto;
import com.novi.webshop.model.Admin;
import com.novi.webshop.model.Employee;
import com.novi.webshop.model.ShoppingCart;
import com.novi.webshop.repository.AdminRepository;
import com.novi.webshop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, EmployeeRepository employeeRepository) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<UserEmployeeDto> divideShoppingCartOrdersToEmployees() {
        List<Admin> adminList = adminRepository.findAll();
        List<Employee> employeeList = employeeRepository.findAll();
        List<UserEmployeeDto> employeeDtoList = new ArrayList<>();
        Admin admin = adminList.get(0);
//        if(adminList.size() == 1) {
//            admin = adminList.get(0);
//        } else {
//            throw new RecordNotFoundException("You either don't have an admin or you have too many admins!" +
//                    "There can only be one admin!");
//        }
        List<ShoppingCart> notProcessedShoppingCarts = admin.getAllNotProcessedShoppingCarts();
        int amountOfNotProcessedShoppingCarts = notProcessedShoppingCarts.size();
        int amountOfEmployees = employeeList.size();
        int ordersPerEmployee = (amountOfNotProcessedShoppingCarts / amountOfEmployees) + 1;
        for (int i = 0; i < amountOfEmployees; i++) {
            int shoppingCartIndex = 0;
//            employeeList.get(i).setShoppingCartList(new ArrayList<>());
            List<ShoppingCart> shoppingCartList = employeeList.get(i).getShoppingCartList();
            while (shoppingCartList.size() < ordersPerEmployee) {

            for (int j = shoppingCartIndex; j < amountOfNotProcessedShoppingCarts; j++) {
                shoppingCartList.add(notProcessedShoppingCarts.get(j));
                    shoppingCartIndex = j;
                }
                employeeList.get(i).setShoppingCartList(shoppingCartList);
                employeeRepository.save(employeeList.get(i));
                employeeDtoList.add(transferToUserEmployeeDto(employeeList.get(i)));
            }
        }
        return employeeDtoList;
    }

    public UserEmployeeDto createEmployeeAccount(UserEmployeeInputDto userEmployeeInputDto) {
        Employee employee = new Employee();
        employee.setEmailAddress(userEmployeeInputDto.getEmailAddress());
        employee.setUsername(userEmployeeInputDto.getUsername());
        employee.setPassword(userEmployeeInputDto.getPassword());
        employee.setFirstName(userEmployeeInputDto.getFirstName());
        employee.setLastName(userEmployeeInputDto.getLastName());
        Employee savedEmployee = employeeRepository.save(employee);
        return transferToUserEmployeeDto(savedEmployee);
    }

    public Admin createAdmin(UserDto userDto) {
        Admin admin = new Admin();
        admin.setUsername(userDto.getUsername());
        adminRepository.save(admin);
        return admin;
    }

    protected UserEmployeeDto transferToUserEmployeeDto(Employee employee) {
        UserEmployeeDto userEmployeeDto = new UserEmployeeDto();
        userEmployeeDto.setEmailAddress(employee.getEmailAddress());
        userEmployeeDto.setFirstName(employee.getFirstName());
        userEmployeeDto.setLastName(employee.getLastName());
        return userEmployeeDto;
    }

}
