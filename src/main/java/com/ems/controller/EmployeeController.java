package com.ems.controller;

import com.ems.entity.Employee;
import com.ems.entity.LeaveRequest;
import com.ems.repository.EmployeeRepository;
import com.ems.service.LeaveService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final LeaveService leaveService;

    public EmployeeController(
            EmployeeRepository employeeRepository,
            LeaveService leaveService) {

        this.employeeRepository = employeeRepository;
        this.leaveService = leaveService;
    }

    // EMPLOYEE PROFILE

    @GetMapping("/employee/profile")
    public String employeeProfile(Model model) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        Employee employee =
                employeeRepository.findByEmail(email);

        model.addAttribute(
                "employee",
                employee
        );

        return "employee-profile";
    }

    // EMPLOYEE LEAVE HISTORY

    @GetMapping("/employee/leaves")
    public String employeeLeaves(Model model) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        List<LeaveRequest> leaves =
                leaveService
                        .getLeavesByEmployeeEmail(
                                email
                        );

        model.addAttribute(
                "leaves",
                leaves
        );

        return "employee-leaves";
    }
}