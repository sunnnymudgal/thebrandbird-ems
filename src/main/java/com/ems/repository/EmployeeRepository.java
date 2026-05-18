package com.ems.repository;

import com.ems.entity.Department;
import com.ems.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    Page<Employee> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    Employee findByEmail(String email);

    long countByDepartment(
            Department department
    );

    List<Employee> findByDepartment(
            Department department
    );
}