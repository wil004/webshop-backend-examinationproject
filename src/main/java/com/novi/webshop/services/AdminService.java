package com.novi.webshop.services;

import com.novi.webshop.dto.UserDto;
import com.novi.webshop.dto.UserEmployeeDto;
import com.novi.webshop.dto.UserEmployeeInputDto;
import com.novi.webshop.model.Admin;

import java.util.List;

public interface AdminService {
    List<UserEmployeeDto> divideShoppingCartOrdersToEmployees(long adminId);

    UserEmployeeDto createEmployeeAccount(UserEmployeeInputDto userEmployeeInputDto);

    Admin createAdmin(UserDto userDto);
}
