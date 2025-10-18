package com.manager.service;

import com.manager.dto.EmployeeDto;
import com.manager.entity.Employee;
import com.manager.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper mapper;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = mapper.map(employeeDto, Employee.class);
        if (employeeDto.getId() != null) {
            employee.setId(null);
        }
        employeeRepository.save(employee);
        return mapper.map(employee, EmployeeDto.class);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.getReferenceById(id);
        return mapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> mapper.map(emp, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee dbEmployee = employeeRepository.findById(employeeDto.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeDto.getId()));
        dbEmployee.setName(employeeDto.getFirstName());
        dbEmployee.setSurname(employeeDto.getLastName());
        dbEmployee.setEmail(employeeDto.getEmail());
        employeeRepository.save(dbEmployee);
        return mapper.map(dbEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        try {
            employeeRepository.delete(employee);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete employee with id: " + id, ex);
        }
    }
}
