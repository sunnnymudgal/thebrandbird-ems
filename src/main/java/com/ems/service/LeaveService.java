package com.ems.service;

import com.ems.entity.LeaveRequest;

import java.util.List;

public interface LeaveService {

    LeaveRequest saveLeave(
            LeaveRequest leaveRequest
    );

    List<LeaveRequest> getAllLeaves();

    LeaveRequest getLeaveById(Long id);

    List<LeaveRequest> getLeavesByEmployeeEmail(
            String email
    );

    long countLeavesByStatus(
            String email,
            String status
    );

    long countByStatus(String status);

    List<LeaveRequest> getRecentLeaves();

    long countEmployeesCurrentlyOnLeave();
}