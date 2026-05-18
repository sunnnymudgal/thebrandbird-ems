package com.ems.controller;

import com.ems.entity.Employee;
import com.ems.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ems.entity.Department;
import com.ems.entity.LeaveRequest;
import com.ems.repository.EmployeeRepository;
import com.ems.service.LeaveService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final LeaveService leaveService;

    public AdminController(
            EmployeeService employeeService,
            EmployeeRepository employeeRepository,
            LeaveService leaveService) {

        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.leaveService = leaveService;
    }    @GetMapping("/dashboard")
    public String dashboard(

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

        // TOTAL EMPLOYEES

        long totalEmployees =
                employeePage.getTotalElements();

        // DEPARTMENTS COUNT

        long totalDepartments = 5;

        // EMPLOYEES ON LEAVE

        long employeesOnLeave =
                leaveService
                        .countEmployeesCurrentlyOnLeave();

        // TOTAL LEAVE REQUESTS

        long totalLeaveRequests =
                leaveService.countByStatus(
                        "PENDING"
                )
                        +
                        leaveService.countByStatus(
                                "APPROVED"
                        )
                        +
                        leaveService.countByStatus(
                                "REJECTED"
                        );

        // PENDING REQUESTS

        long pendingRequests =
                leaveService.countByStatus(
                        "PENDING"
                );

        // RECENT LEAVES

        List<LeaveRequest> recentLeaves =
                leaveService.getRecentLeaves();

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

        model.addAttribute(
                "totalEmployees",
                totalEmployees
        );

        model.addAttribute(
                "employeesOnLeave",
                employeesOnLeave
        );

        model.addAttribute(
                "totalLeaveRequests",
                totalLeaveRequests
        );

        model.addAttribute(
                "pendingRequests",
                pendingRequests
        );

        model.addAttribute(
                "recentLeaves",
                recentLeaves
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

        model.addAttribute(
                "totalDepartments",
                totalDepartments
        );

        return "index";
    }
}