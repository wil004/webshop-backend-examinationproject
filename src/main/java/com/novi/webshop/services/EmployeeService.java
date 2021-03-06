package com.novi.webshop.services;

import com.novi.webshop.dto.UserEmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<UserEmployeeDto> getAllEmployees();

    UserEmployeeDto getEmployeeById(Long employeeId);

    UserEmployeeDto confirmIfOrderIsPaid(Long employeeId, Long orderId, boolean isPaid);

    UserEmployeeDto processOrder(Long employeeId, Long orderId);

    UserEmployeeDto processReturn(Long employeeId, Long returnId);

    UserEmployeeDto addOrderToEmployeeList(Long employeeId, Long orderId);

    List<UserEmployeeDto> divideOrdersOverEmployees();

    UserEmployeeDto addReturnsToEmployeeList(Long employeeId, Long returnsId);

   List<UserEmployeeDto> divideReturnsOverEmployees();
}
