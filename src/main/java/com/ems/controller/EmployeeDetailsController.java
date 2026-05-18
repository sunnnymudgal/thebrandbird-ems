package com.ems.controller;

import com.ems.entity.Employee;
import com.ems.entity.LeaveRequest;
import com.ems.repository.EmployeeRepository;
import com.ems.service.LeaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class EmployeeDetailsController {

    private final EmployeeRepository employeeRepository;
    private final LeaveService leaveService;

    public EmployeeDetailsController(
            EmployeeRepository employeeRepository,
            LeaveService leaveService) {

        this.employeeRepository = employeeRepository;
        this.leaveService = leaveService;
    }

    @GetMapping("/employee/{id}")
    public String employeeDetails(

            @PathVariable Long id,

            Model model) {

        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElse(null);

        if (employee == null) {

            return "redirect:/admin/dashboard";
        }

        // EMPLOYEE LEAVES

        List<LeaveRequest> leaves =
                leaveService
                        .getLeavesByEmployeeEmail(
                                employee.getEmail()
                        );

        // LEAVE COUNTS

        long approvedLeaves =
                leaves.stream()
                        .filter(l ->
                                l.getStatus()
                                        .equals("APPROVED"))
                        .count();

        long pendingLeaves =
                leaves.stream()
                        .filter(l ->
                                l.getStatus()
                                        .equals("PENDING"))
                        .count();

        long rejectedLeaves =
                leaves.stream()
                        .filter(l ->
                                l.getStatus()
                                        .equals("REJECTED"))
                        .count();

        model.addAttribute(
                "employee",
                employee
        );

        model.addAttribute(
                "leaves",
                leaves
        );

        model.addAttribute(
                "approvedLeaves",
                approvedLeaves
        );

        model.addAttribute(
                "pendingLeaves",
                pendingLeaves
        );

        model.addAttribute(
                "rejectedLeaves",
                rejectedLeaves
        );

        return "employee-details";
    }
}