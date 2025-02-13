package com.zj.careconnect.service;

import com.zj.careconnect.DTO.DoctorRegistrationDto;
import com.zj.careconnect.model.User;
import com.zj.careconnect.repo.UserRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    // Check if a user with the same email or phone exists
    public void validateUserUniqueness(String email, String phone) {
        if (userRepo.existsByEmailOrPhone(email, phone)) {
            throw new IllegalArgumentException("User with this email or phone already exists.");
        }
    }

    // Save a user
    public User saveUser(User user) {
        return userRepo.save(user);
    }

}
