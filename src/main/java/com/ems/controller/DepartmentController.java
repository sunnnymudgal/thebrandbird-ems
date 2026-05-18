package com.ems.controller;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/departments")
public class DepartmentController {

    private final EmployeeRepository employeeRepository;

    public DepartmentController(
            EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    // ALL DEPARTMENTS

    @GetMapping
    public String departments(Model model) {

        model.addAttribute(
                "departments",
                Department.values()
        );

        model.addAttribute(
                "employeeRepository",
                employeeRepository
        );

        return "departments";
    }

    // SINGLE DEPARTMENT EMPLOYEES

    @GetMapping("/{department}")
    public String departmentEmployees(

            @PathVariable Department department,

            Model model) {

        List<Employee> employees =
                employeeRepository
                        .findByDepartment(
                                department
                        );

        model.addAttribute(
                "department",
                department
        );

        model.addAttribute(
                "employees",
                employees
        );

        return "department-employees";
    }

    @GetMapping("/employee/{id}")
    public String employeeDetails(

            @PathVariable Long id,

            Model model) {

        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElse(null);

        model.addAttribute(
                "employee",
                employee
        );

        return "employee-details";
    }
}