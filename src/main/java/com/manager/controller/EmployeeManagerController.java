package com.manager.controller;

import com.manager.dto.EmployeeDto;
import com.manager.exception.ResourceNotFoundException;
import com.manager.service.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class EmployeeManagerController {

    private final EmployeeServiceImpl employeeServiceImpl;

    @GetMapping("/")
    public String hello() {
        return "Employee Manager App Welcome!";
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id) {
        EmployeeDto employee = employeeServiceImpl.getEmployeeById(id);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeServiceImpl.getEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("Employee created successfully!");
        EmployeeDto createdEmployee = employeeServiceImpl.createEmployee(employeeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto) {
        log.info("Updating employee with id: {}", id);
        employeeDto.setId(id);
        EmployeeDto updated = employeeServiceImpl.updateEmployee(employeeDto);
        String message = String.format(
            "Employee with %d successfully updated\nNow it's:\nid: %d\nfirstName: %s\nlastName: %s\nemail: %s",
            id, updated.getId(), updated.getFirstName(), updated.getLastName(), updated.getEmail()
        );
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        log.info("Deleting employee with id: {}", id);
        try {
            employeeServiceImpl.deleteEmployee(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to delete employee with id: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
