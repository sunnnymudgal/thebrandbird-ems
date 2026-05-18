package com.ems.service;

import com.ems.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    Page<Employee> getAllEmployees(
            int page,
            int size,
            String keyword
    );

    Employee getEmployeeById(Long id);

    void deleteEmployee(Long id);
}