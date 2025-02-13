package com.zj.careconnect.DTO;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String role; // e.g., "PATIENT", "DOCTOR", "ADMIN"
    private String phone;
}

