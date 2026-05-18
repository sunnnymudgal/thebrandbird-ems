package com.ems.controller;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.service.LeaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
public class ReportController {

    private final EmployeeRepository employeeRepository;
    private final LeaveService leaveService;

    public ReportController(
            EmployeeRepository employeeRepository,
            LeaveService leaveService) {

        this.employeeRepository = employeeRepository;
        this.leaveService = leaveService;
    }

    @GetMapping("/admin/reports")
    public String reports(Model model) {

        List<Employee> employees =
                employeeRepository.findAll();

        // TOTAL EMPLOYEES

        long totalEmployees =
                employees.size();

        // TOTAL PAYROLL

        double totalPayroll =
                employees.stream()
                        .mapToDouble(Employee::getSalary)
                        .sum();

        // HIGHEST PAID EMPLOYEE

        Employee highestPaidEmployee =
                employees.stream()
                        .max(Comparator.comparing(
                                Employee::getSalary
                        ))
                        .orElse(null);

        // LOWEST PAID EMPLOYEE

        Employee lowestPaidEmployee =
                employees.stream()
                        .min(Comparator.comparing(
                                Employee::getSalary
                        ))
                        .orElse(null);

        // LEAVE ANALYTICS

        long approvedLeaves =
                leaveService.countByStatus(
                        "APPROVED"
                );

        long pendingLeaves =
                leaveService.countByStatus(
                        "PENDING"
                );

        long rejectedLeaves =
                leaveService.countByStatus(
                        "REJECTED"
                );

        // EMPLOYEES CURRENTLY ON LEAVE

        long employeesOnLeave =
                leaveService
                        .countEmployeesCurrentlyOnLeave();

        // DEPARTMENT COUNTS

        long developmentCount =
                employeeRepository.countByDepartment(
                        Department.DEVELOPMENT
                );

        long designCount =
                employeeRepository.countByDepartment(
                        Department.UI_UX_DESIGN
                );

        long marketingCount =
                employeeRepository.countByDepartment(
                        Department.DIGITAL_MARKETING
                );

        long seoCount =
                employeeRepository.countByDepartment(
                        Department.SEO
                );

        long contentCount =
                employeeRepository.countByDepartment(
                        Department.CONTENT_CREATION
                );

        // MODEL

        model.addAttribute(
                "totalEmployees",
                totalEmployees
        );

        model.addAttribute(
                "totalPayroll",
                totalPayroll
        );

        model.addAttribute(
                "highestPaidEmployee",
                highestPaidEmployee
        );

        model.addAttribute(
                "lowestPaidEmployee",
                lowestPaidEmployee
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

        model.addAttribute(
                "employeesOnLeave",
                employeesOnLeave
        );

        model.addAttribute(
                "developmentCount",
                developmentCount
        );

        model.addAttribute(
                "designCount",
                designCount
        );

        model.addAttribute(
                "marketingCount",
                marketingCount
        );

        model.addAttribute(
                "seoCount",
                seoCount
        );

        model.addAttribute(
                "contentCount",
                contentCount
        );

        return "reports";
    }
}