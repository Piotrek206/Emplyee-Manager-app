package com.manager;

import com.manager.dto.EmployeeDto;
import com.manager.entity.Employee;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.typeMap(EmployeeDto.class, Employee.class)
                .addMappings(m -> {
                    m.map(EmployeeDto::getFirstName, Employee::setName);
                    m.map(EmployeeDto::getLastName, Employee::setSurname);
                });

        mapper.typeMap(Employee.class, EmployeeDto.class)
                .addMappings(m -> {
                    m.map(Employee::getName, EmployeeDto::setFirstName);
                    m.map(Employee::getSurname, EmployeeDto::setLastName);
                });

        return mapper;
    }
}
