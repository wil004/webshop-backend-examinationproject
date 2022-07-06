package com.novi.webshop.controller;

import com.novi.webshop.dto.AdminDto;
import com.novi.webshop.dto.UserDto;
import com.novi.webshop.dto.UserEmployeeDto;
import com.novi.webshop.dto.UserEmployeeInputDto;
import com.novi.webshop.model.Admin;
import com.novi.webshop.services.AdminServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("user")
public class AdminController {
    private final AdminServiceImpl adminServiceImpl;

    public AdminController(AdminServiceImpl adminServiceImpl) {
        this.adminServiceImpl = adminServiceImpl;
    }

    @PostMapping(path = "create-employee", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserEmployeeDto> createEmployeeAccount( @RequestBody UserEmployeeInputDto userEmployeeInputDto) {
        final URI location = URI.create("/employee" + userEmployeeInputDto.getUsername());
        return ResponseEntity.created(location).body(adminServiceImpl.createEmployeeAccount(userEmployeeInputDto));
    }

    @PostMapping(path = "create-admin", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminDto adminDto) {
        final URI location = URI.create("/employee" + adminDto.getUsername());
        return ResponseEntity.created(location).body(adminServiceImpl.createAdmin(adminDto));
    }
}
