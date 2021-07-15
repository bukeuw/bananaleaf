package com.ato.bananaleaf.controller;

import com.ato.bananaleaf.dto.EmployeeDto;
import com.ato.bananaleaf.entity.Department;
import com.ato.bananaleaf.entity.Employee;
import com.ato.bananaleaf.repository.DepartmentRepository;
import com.ato.bananaleaf.repository.EmployeeRepository;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping
    public String allEmployee(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);

        return "employee/employee-list";
    }

    @GetMapping("/create")
    public String createEmployee(Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);

        return "employee/employee-form";
    }

    @PostMapping
    public String storeEmployee(@Valid EmployeeDto employeeDto, BindingResult result) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setDob(employeeDto.getDob());
        employee.setEducation(employeeDto.getEducation());
        employee.setJob(employeeDto.getJob());
        Department department = departmentRepository.findById(employeeDto.getDepartmentId()).get();
        employee.setDepartment(department);

        if (result.hasErrors()) {
            return "redirect:/employee/create";
        }

        employeeRepository.save(employee);

        return "redirect:/employee";
    }
}
