package com.zj.careconnect.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRegistrationDto extends UserRegistrationDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String gender;
    private String address;
}
