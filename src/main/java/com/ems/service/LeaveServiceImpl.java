package com.ems.service;

import com.ems.entity.LeaveRequest;
import com.ems.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveServiceImpl
        implements LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveServiceImpl(
            LeaveRepository leaveRepository) {

        this.leaveRepository = leaveRepository;
    }

    @Override
    public LeaveRequest saveLeave(
            LeaveRequest leaveRequest) {

        return leaveRepository.save(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getAllLeaves() {

        return leaveRepository.findAll();
    }

    @Override
    public LeaveRequest getLeaveById(Long id) {

        return leaveRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public List<LeaveRequest> getLeavesByEmployeeEmail(
            String email) {

        return leaveRepository
                .findByEmployeeEmail(email);
    }

    @Override
    public long countLeavesByStatus(
            String email,
            String status) {

        return leaveRepository
                .countByEmployeeEmailAndStatus(
                        email,
                        status
                );
    }

    @Override
    public long countByStatus(String status) {

        return leaveRepository.countByStatus(status);
    }

    @Override
    public List<LeaveRequest> getRecentLeaves() {

        return leaveRepository
                .findTop5ByOrderByIdDesc();
    }
    @Override
    public long countEmployeesCurrentlyOnLeave() {

        return leaveRepository
                .countEmployeesCurrentlyOnLeave(
                        LocalDate.now()
                );
    }
}