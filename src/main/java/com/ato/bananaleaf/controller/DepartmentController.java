package com.ato.bananaleaf.controller;

import com.ato.bananaleaf.entity.Department;
import com.ato.bananaleaf.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping
    public String allDepartment(Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);

        return "department/department-list";
    }

    @GetMapping("/create")
    public String createDepartment() {
        return "department/department-form";
    }

    @PostMapping
    public String storeDepartment(@Valid Department department, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/department/create";
        }

        departmentRepository.save(department);

        return "redirect:/department";
    }
}
