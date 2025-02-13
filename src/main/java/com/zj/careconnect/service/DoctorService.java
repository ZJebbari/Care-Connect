package com.zj.careconnect.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zj.careconnect.DTO.DoctorRegistrationDto;
import com.zj.careconnect.enums.Role;
import com.zj.careconnect.exception.DuplicateEntityException;
import com.zj.careconnect.exception.EntityNotFoundException;
import com.zj.careconnect.model.Availability;
import com.zj.careconnect.model.Doctor;
import com.zj.careconnect.model.User;
import com.zj.careconnect.repo.DoctorRepo;
import com.zj.careconnect.repo.UserRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final UserService userService;
    private final UserRepo   userRepo;

    public Doctor addDoctor(String doctorJson, MultipartFile photo) throws IOException {
        // Use the configured ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule

        DoctorRegistrationDto dto = objectMapper.readValue(doctorJson, DoctorRegistrationDto.class);

        // Check if email or phone already exists
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new DuplicateEntityException("A Doctor with the email " + dto.getEmail() + " already exists.");
        }
        if (userRepo.existsByPhone(dto.getPhone())) {
            throw new DuplicateEntityException("A Doctor with the phone number " + dto.getPhone() + " already exists.");
        }

        // Create User entity
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.DOCTOR);
        user.setPhone(dto.getPhone());

        // Create Doctor entity
        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setAbout(dto.getAbout());
        doctor.setPhoto(photo.getBytes());

        // Convert List<AvailabilityDto> to List<Availability>
        List<Availability> availabilities = dto.getAvailability().stream()
                .map(availabilityDto -> {
                    Availability availability = new Availability();
                    availability.setDayOfWeek(availabilityDto.getDayOfWeek());
                    availability.setStartTime(availabilityDto.getStartTime());
                    availability.setEndTime(availabilityDto.getEndTime());
                    availability.setDoctor(doctor);
                    return availability;
                })
                .collect(Collectors.toList());

        doctor.setAvailability(availabilities);

        // Save the user and assign it to the doctor
        User savedUser = userService.saveUser(user);
        doctor.setUser(savedUser);

        // Save the doctor
        return doctorRepo.save(doctor);
    }



    public Doctor updateDoctor(@Valid DoctorRegistrationDto dto, int id) {

        Optional<Doctor> doctor = doctorRepo.findById(id);
        Doctor existingDoctor = unwrapDoctor(doctor,id);

        if(dto.getAbout()!=null && !dto.getAbout().isEmpty()) existingDoctor.setAbout(dto.getAbout());

        Optional<User> user = userRepo.findById(existingDoctor.getUser().getId());
        User existingUser = unwrapUser(user,existingDoctor.getUser().getId());

        if(dto.getName()!=null && !dto.getName().isEmpty()) existingUser.setName(dto.getName());
        if(dto.getPhone()!=null && !dto.getPhone().isEmpty()) existingUser.setPhone(dto.getPhone());
        if(dto.getEmail()!=null && !dto.getEmail().isEmpty()) existingUser.setEmail(dto.getEmail());

        existingDoctor.setUser(existingUser);
        return doctorRepo.save(existingDoctor);
    }

    public Doctor getDoctorById(int id) {
        Optional<Doctor> doctor = doctorRepo.findById(id);
        return unwrapDoctor(doctor,id);
    }

    public void deleteDoctorById(int id) {
        Doctor doctor = unwrapDoctor(doctorRepo.findById(id),id);
        doctorRepo.delete(doctor);
    }

    public List<Doctor> getDoctors() {
        List<Doctor> doctors = doctorRepo.findAll();
        if (doctors.isEmpty()) {
            throw new EntityNotFoundException("No Doctor found.");
        }
        return doctors;
    }

    static Doctor unwrapDoctor(Optional<Doctor> entity, int id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Doctor.class);
    }

    static User unwrapUser(Optional<User> entity, int id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }

}

