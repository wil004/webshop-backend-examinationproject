package com.novi.webshop.controller;

import com.novi.webshop.dto.UserEmployeeDto;
import com.novi.webshop.services.EmployeeServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")

    public ResponseEntity<List<UserEmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/id={employeeId}")
    public ResponseEntity<UserEmployeeDto> getEmployeeById(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }


    @PutMapping(value = "confirm-payment/employee-id={employeeId}/order-id={orderId}/ispaid={isPaid}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserEmployeeDto> confirmIfOrderIsPaid(@PathVariable Long employeeId, @PathVariable Long orderId, @PathVariable boolean isPaid) {
        return ResponseEntity.ok(employeeService.confirmIfOrderIsPaid(employeeId, orderId, isPaid));
    }

    @PutMapping(value = "process-order/employee-id={employeeId}/order-id={orderId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserEmployeeDto> processOrder(@PathVariable Long employeeId, @PathVariable Long orderId) {
        return ResponseEntity.ok(employeeService.processOrder(employeeId, orderId));
    }

    @PutMapping(value = "id={employeeId}/order-id={orderId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<UserEmployeeDto> addOrderToEmployeeList(@PathVariable Long employeeId,@PathVariable Long orderId) {
        return ResponseEntity.ok(employeeService.addOrderToEmployeeList(employeeId, orderId));
    }

    @PutMapping(value = "divide-orders",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserEmployeeDto>> divideOrdersOverEmployees() {
        return ResponseEntity.ok(employeeService.divideOrdersOverEmployees());
    }

    public ResponseEntity<UserEmployeeDto> divideOrdersOverEmployees(@PathVariable Long employeeId,@PathVariable Long orderId) {
        return ResponseEntity.ok(employeeService.addOrderToEmployeeList(employeeId, orderId));
    }

}
