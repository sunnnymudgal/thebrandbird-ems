package com.ems.repository;

import com.ems.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository
        extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);
}