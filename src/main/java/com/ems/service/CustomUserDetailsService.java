package com.ems.service;

import com.ems.entity.Admin;
import com.ems.entity.Employee;
import com.ems.repository.AdminRepository;
import com.ems.repository.EmployeeRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(
            AdminRepository adminRepository,
            EmployeeRepository employeeRepository) {

        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String email)
            throws UsernameNotFoundException {

        // CHECK ADMIN

        Admin admin =
                adminRepository.findByEmail(email);

        if (admin != null) {

            return new User(
                    admin.getEmail(),
                    admin.getPassword(),
                    List.of(
                            new SimpleGrantedAuthority(
                                    "ROLE_ADMIN"))
            );
        }

        // CHECK EMPLOYEE

        Employee employee =
                employeeRepository.findByEmail(email);

        if (employee != null) {

            return new User(
                    employee.getEmail(),
                    employee.getPassword(),
                    List.of(
                            new SimpleGrantedAuthority(
                                    "ROLE_EMPLOYEE"))
            );
        }

        throw new UsernameNotFoundException(
                "User not found");
    }
}