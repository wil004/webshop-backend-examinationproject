package com.novi.webshop.services;

import com.novi.webshop.controller.exceptions.RecordNotFoundException;
import com.novi.webshop.dto.AdminDto;
import com.novi.webshop.dto.UserEmployeeDto;
import com.novi.webshop.dto.UserEmployeeInputDto;
import com.novi.webshop.helpers.TransferModelToDto;
import com.novi.webshop.model.Admin;
import com.novi.webshop.model.Employee;
import com.novi.webshop.repository.AdminRepository;
import com.novi.webshop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final UserServiceImpl userService;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, EmployeeRepository employeeRepository, UserServiceImpl userService) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.userService = userService;
    }

    @Override
    public UserEmployeeDto createEmployeeAccount(UserEmployeeInputDto userEmployeeInputDto, Long adminId) {
        if (!userService.doesUsernameAlreadyExist(userEmployeeInputDto.getUsername())) {
            Employee employee = new Employee();
            employee.setEmailAddress(userEmployeeInputDto.getEmailAddress());
            employee.setUsername(userEmployeeInputDto.getUsername());
            employee.setPassword(userEmployeeInputDto.getPassword());
            employee.setFirstName(userEmployeeInputDto.getFirstName());
            employee.setLastName(userEmployeeInputDto.getLastName());
            if (adminRepository.findById(adminId).isPresent()) {
                employee.setAdmin(adminRepository.findById(adminId).orElseThrow());
            } else {
                throw new RecordNotFoundException("Employee must be connected with an admin");
            }
            Employee savedEmployee = employeeRepository.save(employee);
            return TransferModelToDto.transferToUserEmployeeDto(savedEmployee);
        } else {
            throw new RecordNotFoundException("Username already exists");
        }
    }

    @Override
    public Admin createAdmin(AdminDto adminDto) {
        if(adminRepository.findAll().size() >= 1) {
            throw new RecordNotFoundException("There is already an admin");
        } else {
            Admin admin = new Admin();
            admin.setEmailAddress(adminDto.getEmailAddress());
            admin.setUsername(adminDto.getUsername());
            admin.setPassword(adminDto.getPassword());
            admin.setBankAccount(adminDto.getBankAccount());
            adminRepository.save(admin);
            return admin;
        }
    }


}
