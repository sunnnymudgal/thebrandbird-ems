package com.ems.controller;

import com.ems.entity.Employee;
import com.ems.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminEmployeeController {

    private final EmployeeService employeeService;

    public AdminEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String home(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "")
            String keyword,

            Model model) {

        Page<Employee> employeePage =
                employeeService.getAllEmployees(
                        page,
                        size,
                        keyword
                );

        model.addAttribute(
                "employees",
                employeePage.getContent()
        );

        model.addAttribute(
                "currentPage",
                page
        );

        model.addAttribute(
                "totalPages",
                employeePage.getTotalPages()
        );

        model.addAttribute(
                "keyword",
                keyword
        );

        return "index";
    }

    @GetMapping("/add")
    public String addEmployeeForm(Model model) {

        model.addAttribute(
                "employee",
                new Employee());

        return "add-employee";
    }

    @PostMapping("/save")
    public String saveEmployee(

            @ModelAttribute Employee employee,

            @RequestParam("imageFile")
            MultipartFile file)

            throws IOException {

        // IMAGE TO BYTE[]

        if (!file.isEmpty()) {

            employee.setProfileImage(
                    file.getBytes()
            );
        }

        employeeService.saveEmployee(employee);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(
            @PathVariable Long id,
            Model model) {

        Employee employee =
                employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);

        return "edit-employee";
    }

    @PostMapping("/update")
    public String updateEmployee(

            @ModelAttribute Employee employee,

            @RequestParam("imageFile")
            MultipartFile file)

            throws IOException {

        // GET OLD EMPLOYEE

        Employee existingEmployee =
                employeeService
                        .getEmployeeById(
                                employee.getId()
                        );

        // KEEP OLD IMAGE IF NEW NOT UPLOADED

        if (file.isEmpty()) {

            employee.setProfileImage(
                    existingEmployee.getProfileImage()
            );

        } else {

            // NEW IMAGE

            employee.setProfileImage(
                    file.getBytes()
            );
        }

        employeeService.saveEmployee(employee);

        return "redirect:/admin/dashboard";
    }
}