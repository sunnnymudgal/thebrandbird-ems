package com.ems.controller;

import com.ems.entity.LeaveRequest;
import com.ems.service.LeaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(
            LeaveService leaveService) {

        this.leaveService = leaveService;
    }

    // SHOW ALL LEAVES

    @GetMapping
    public String leavePage(Model model) {

        model.addAttribute(
                "leaves",
                leaveService.getAllLeaves()
        );

        return "leave-management";
    }

    // SHOW APPLY FORM

    @GetMapping("/apply")
    public String applyLeaveForm(Model model) {

        model.addAttribute(
                "leaveRequest",
                new LeaveRequest()
        );

        return "apply-leave";
    }

    // SAVE LEAVE



    // APPROVE LEAVE

    @GetMapping("/approve/{id}")
    public String approveLeave(
            @PathVariable Long id) {

        LeaveRequest leave =
                leaveService.getLeaveById(id);

        leave.setStatus("APPROVED");

        leaveService.saveLeave(leave);

        return "redirect:/leave";
    }

    // REJECT LEAVE

    @GetMapping("/reject/{id}")
    public String rejectLeave(
            @PathVariable Long id) {

        LeaveRequest leave =
                leaveService.getLeaveById(id);

        leave.setStatus("REJECTED");

        leaveService.saveLeave(leave);

        return "redirect:/leave";
    }

    @PostMapping("/save")
    public String saveLeave(
            @ModelAttribute LeaveRequest leaveRequest) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        leaveRequest.setEmployeeEmail(email);

        leaveService.saveLeave(leaveRequest);

        return "redirect:/employee/leaves";
    }
}