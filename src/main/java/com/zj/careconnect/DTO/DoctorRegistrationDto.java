package com.zj.careconnect.DTO;

import com.zj.careconnect.enums.Specialty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class DoctorRegistrationDto extends UserRegistrationDto {
    private byte[] photo; // Store the photo as binary data (BLOB)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialty specialty;
    private String about;
    private List<AvailabilityDto> availability;
}
