package com.ato.bananaleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {
    @GetMapping("/employee")
    public String allEmployee(Model model) {
        model.addAttribute("employee", "Ato Lie");
        return "employee-list";
    }
}
