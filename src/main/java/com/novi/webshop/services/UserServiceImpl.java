package com.novi.webshop.services;

import com.novi.webshop.model.Admin;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Employee;
import com.novi.webshop.repository.AdminRepository;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;


    public UserServiceImpl(AdminRepository adminRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean doesUsernameAlreadyExist(String username) {
        List<Admin> admin = adminRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        List<Customer> customers = customerRepository.findAll();

        for(int i = 0; i < admin.size(); i++) {
            if(admin.get(i).getUsername() != null) {
                if (admin.get(i).getUsername().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        }
        for(int i = 0; i < employees.size(); i++) {
            if(employees.get(i).getUsername() != null) {
                if (employees.get(i).getUsername().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        }
        for(int i = 0; i < customers.size(); i++) {
            if(customers.get(i).getUsername() != null) {
                if (customers.get(i).getUsername().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        }
        return false;
    }

}
