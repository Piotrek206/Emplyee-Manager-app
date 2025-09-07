package com.manager.controller;

import com.manager.dto.EmployeeDto;
import com.manager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class EmployeeManagerController {

    private final EmployeeService employeeService;

    @GetMapping("/")
    public String hello() {
        return "Hello, World!";
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("Employee created successfully!");
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdEmployee);
    }
}
