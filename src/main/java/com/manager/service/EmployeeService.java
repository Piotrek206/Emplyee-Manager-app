package com.manager.service;

import com.manager.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employee);

    EmployeeDto getEmployeeById(Long id);

    List<EmployeeDto> getEmployees();

    EmployeeDto updateEmployee(EmployeeDto employee); // employee.id must be set

    void deleteEmployee(Long id);
}
