package com.novi.webshop.security;

import com.novi.webshop.model.Admin;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Employee;
import com.novi.webshop.repository.AdminRepository;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsernameSecurityService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public UsernameSecurityService(AdminRepository adminRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Admin> admins = new ArrayList<>(adminRepository.findAll());
        Admin admin = new Admin();
        Employee employee = new Employee();
        Customer customer = new Customer();
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getUsername().equalsIgnoreCase(username)) {
                admin = admins.get(i);
            }
        }
        if(admin.getUsername() == null) {
            List<Employee> employees = new ArrayList<>(employeeRepository.findAll());
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getUsername().equalsIgnoreCase(username)) {
                    employee = employees.get(i);
                }
            }
        } if (employee.getUsername() == null) {
            List<Customer> customers = new ArrayList<>(customerRepository.findAll());
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getUsername().equalsIgnoreCase(username)) {
                    customer = customers.get(i);
                }
            }
        }



        if (admin.getUsername() != null) {
            return User.withUsername(admin.getUsername()).password(admin.getPassword()).authorities(admin.getRole()).build();
        } else if (employee.getUsername() != null) {
            return User.withUsername(employee.getUsername()).password(employee.getPassword()).authorities(employee.getRole()).build();
        } else if (customer.getUsername() != null) {
            return User.withUsername(customer.getUsername()).password(customer.getPassword()).authorities(customer.getRole()).build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

}
