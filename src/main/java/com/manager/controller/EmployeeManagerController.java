package com.manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeManagerController {

    @GetMapping("/")
    public String hello() {
        return "Hello, World!";
    }
}
