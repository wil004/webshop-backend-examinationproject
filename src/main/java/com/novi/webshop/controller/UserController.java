package com.novi.webshop.controller;

import com.novi.webshop.dto.UserDto;
import com.novi.webshop.dto.UserEmployeeDto;
import com.novi.webshop.dto.UserEmployeeInputDto;
import com.novi.webshop.model.Admin;
import com.novi.webshop.services.AdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final AdminService adminService;

    public UserController(AdminService adminService) {
        this.adminService = adminService;
    }

//    @PutMapping("/divide-shoppingcarts-employees")
//    public ResponseEntity<List<UserEmployeeDto>> divideShoppingCarts(){
//        return ResponseEntity.ok(adminService.divideShoppingCartOrdersToEmployees());
//    }

    @PostMapping(path = "create-employee", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserEmployeeDto> createEmployeeAccount(@RequestBody UserEmployeeInputDto userEmployeeInputDto) {
        final URI location = URI.create("/employee" + userEmployeeInputDto.getUsername());
        return ResponseEntity.created(location).body(adminService.createEmployeeAccount(userEmployeeInputDto));
    }

    @PostMapping(path = "create-admin", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Admin> createAdmin(@RequestBody UserDto userDto) {
        final URI location = URI.create("/employee" + userDto.getUsername());
        return ResponseEntity.created(location).body(adminService.createAdmin(userDto));
    }
}
