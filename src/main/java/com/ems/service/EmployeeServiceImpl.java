package com.ems.service;

import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl
        implements EmployeeService {

    private final EmployeeRepository employeeRepository;


    private final BCryptPasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            BCryptPasswordEncoder passwordEncoder) {

        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        employee.setPassword(
                passwordEncoder.encode(
                        employee.getPassword()
                )
        );

        return employeeRepository.save(employee);
    }
    @Override
    public Page<Employee> getAllEmployees(
            int page,
            int size,
            String keyword) {

        Pageable pageable =
                PageRequest.of(page, size);

        if (keyword != null &&
                !keyword.isEmpty()) {

            return employeeRepository
                    .findByNameContainingIgnoreCase(
                            keyword,
                            pageable
                    );
        }

        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee getEmployeeById(Long id) {

        return employeeRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public void deleteEmployee(Long id) {

        employeeRepository.deleteById(id);
    }
}