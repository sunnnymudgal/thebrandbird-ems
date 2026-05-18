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
public class EmployeeDashboardController {

    private final EmployeeRepository employeeRepository;
    private final LeaveService leaveService;

    public EmployeeDashboardController(
            EmployeeRepository employeeRepository,
            LeaveService leaveService) {

        this.employeeRepository = employeeRepository;
        this.leaveService = leaveService;
    }

    @GetMapping("/employee/dashboard")
    public String employeeDashboard(
            Model model) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        Employee employee =
                employeeRepository.findByEmail(email);

        // LEAVE COUNTS

        long pendingLeaves =
                leaveService.countLeavesByStatus(
                        email,
                        "PENDING"
                );

        long approvedLeaves =
                leaveService.countLeavesByStatus(
                        email,
                        "APPROVED"
                );

        long rejectedLeaves =
                leaveService.countLeavesByStatus(
                        email,
                        "REJECTED"
                );

        // RECENT LEAVES

        List<LeaveRequest> recentLeaves =
                leaveService.getLeavesByEmployeeEmail(
                        email
                );

        model.addAttribute(
                "employee",
                employee
        );

        model.addAttribute(
                "pendingLeaves",
                pendingLeaves
        );

        model.addAttribute(
                "approvedLeaves",
                approvedLeaves
        );

        model.addAttribute(
                "rejectedLeaves",
                rejectedLeaves
        );

        model.addAttribute(
                "recentLeaves",
                recentLeaves
        );

        return "employee-dashboard";
    }
}