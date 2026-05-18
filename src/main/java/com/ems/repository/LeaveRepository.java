package com.ems.repository;

import com.ems.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRepository
        extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeEmail(
            String employeeEmail
    );

    long countByEmployeeEmailAndStatus(
            String employeeEmail,
            String status
    );

    long countByStatus(String status);
    List<LeaveRequest> findTop5ByOrderByIdDesc();

    @Query("""
SELECT COUNT(l)
FROM LeaveRequest l
WHERE l.status = 'APPROVED'
AND :today BETWEEN l.startDate AND l.endDate
""")
    long countEmployeesCurrentlyOnLeave(
            LocalDate today
    );
}