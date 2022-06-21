package com.novi.webshop.security;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.model.Admin;
import com.novi.webshop.model.Customer;
import com.novi.webshop.model.Employee;
import com.novi.webshop.repository.AdminRepository;
import com.novi.webshop.repository.CustomerRepository;
import com.novi.webshop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    public AuthController(AdminRepository adminRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/auth/admin")
    public ResponseEntity<Object> signInAdmin(@RequestBody AuthDto authDto) {
        boolean userNameDoesExist = false;

        List<Admin> admin = new ArrayList<>(adminRepository.findAll());

        for (int i = 0; i < admin.size(); i++) {
            if (authDto.getUsername().equalsIgnoreCase(admin.get(i).getUsername())) {
                userNameDoesExist = true;
            }
        }

        if (userNameDoesExist) {
            UsernamePasswordAuthenticationToken up =
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated :" + token);
        }
            throw new RecordNotFoundException("Username not found");
    }

    @PostMapping("auth/employee")
    public ResponseEntity<Object> signInEmployee(@RequestBody AuthDto authDto) {
        boolean userNameDoesExist = false;

        List<Employee> employees = new ArrayList<>(employeeRepository.findAll());

        for (int i = 0; i < employees.size(); i++) {
            if (authDto.getUsername().equalsIgnoreCase(employees.get(i).getUsername())) {
                userNameDoesExist = true;
            }
        }

        if (userNameDoesExist) {
            UsernamePasswordAuthenticationToken up =
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated :" + token);
        }
        throw new RecordNotFoundException("Username not found");
    }

    @PostMapping("auth/customer")
    public ResponseEntity<Object> signInCustomer(@RequestBody AuthDto authDto) {
        boolean userNameDoesExist = false;

        List<Customer> customer = new ArrayList<>(customerRepository.findAll());

        for (int i = 0; i < customer.size(); i++) {
            if (authDto.getUsername().equalsIgnoreCase(customer.get(i).getUsername())) {
                userNameDoesExist = true;
            }
        }

        if (userNameDoesExist) {
            UsernamePasswordAuthenticationToken up =
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated :" + token);
        }
        throw new RecordNotFoundException("Username not found");
    }

}
