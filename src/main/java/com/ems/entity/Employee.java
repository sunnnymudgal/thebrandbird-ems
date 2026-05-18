package com.ems.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Department department;

    private String role = "EMPLOYEE";

    private Double salary;

    private String phone;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] profileImage;

    public String getProfileImageBase64() {

        if (profileImage == null) {

            return null;
        }

        return java.util.Base64
                .getEncoder()
                .encodeToString(profileImage);
    }
}