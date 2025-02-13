package com.zj.careconnect.service;

import com.zj.careconnect.DTO.PatientRegistrationDto;
import com.zj.careconnect.enums.Role;
import com.zj.careconnect.exception.DuplicateEntityException;
import com.zj.careconnect.exception.EntityNotFoundException;
import com.zj.careconnect.model.Patient;
import com.zj.careconnect.model.User;
import com.zj.careconnect.repo.PatientRepo;
import com.zj.careconnect.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepo patientRepo;
    private final UserRepo userRepo;

    public Patient registerPatient(PatientRegistrationDto dto) {

        // Check if email or phone already exists
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new DuplicateEntityException("A patient with the email " + dto.getEmail() + " already exists.");
        }

        if (userRepo.existsByPhone(dto.getPhone())) {
            throw new DuplicateEntityException("A patient with the phone number " + dto.getPhone() + " already exists.");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.PATIENT);
        user.setPhone(dto.getPhone());

        // Create Patient entity
        Patient patient = new Patient();
        patient.setBirthday(dto.getBirthday());
        patient.setGender(dto.getGender());
        patient.setAddress(dto.getAddress());

        // Save the user and patient entities (example of repository calls)
        userRepo.save(user); // Assuming userRepo is injected into the service
        patient.setUser(user); // Associate user with the patient
        return patientRepo.save(patient); // Assuming patientRepo is injected
    }

    public Patient updatePatient(int id, PatientRegistrationDto dto) {
        Optional<Patient> patient = patientRepo.findById(id);
        Patient existingPatient = unwrapPatient(patient, id);

        // Update patient fields
        if (dto.getBirthday() != null) existingPatient.setBirthday(dto.getBirthday());
        if (dto.getGender() != null) existingPatient.setGender(dto.getGender());
        if (dto.getAddress() != null) existingPatient.setAddress(dto.getAddress());

        // Update associated user fields
        Optional<User> user = userRepo.findById(existingPatient.getUser().getId());
        User existingUser = unwrapUser(user, existingPatient.getUser().getId());

        // Validate unique fields
        if (dto.getEmail() != null && userRepo.existsByEmailAndIdNot(dto.getEmail(), existingUser.getId())) {
            throw new DuplicateEntityException("Email is already in use by another user.");
        }
        if (dto.getPhone() != null && userRepo.existsByPhoneAndIdNot(dto.getPhone(), existingUser.getId())) {
            throw new DuplicateEntityException("Phone number is already in use by another user.");
        }

        // Update user fields
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()) existingUser.setEmail(dto.getEmail());
        if (dto.getPhone() != null) existingUser.setPhone(dto.getPhone());
        if (dto.getName() != null) existingUser.setName(dto.getName());

        userRepo.save(existingUser);
        return patientRepo.save(existingPatient);
    }

    public Patient getPatientById(int id) {
        Optional<Patient> patient = patientRepo.findById(id);
        return unwrapPatient(patient,id);
    }

    public void deletePatient(int id) {
        Patient patient = unwrapPatient(patientRepo.findById(id),id);
        patientRepo.delete(patient);
    }

    public List<Patient> getPatients() {
        List<Patient> patients = patientRepo.findAll();
        if (patients.isEmpty()) {
            throw new EntityNotFoundException("No patients found.");
        }
        return patients;
    }


    static Patient unwrapPatient(Optional<Patient> entity, int id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Patient.class);
    }

    static User unwrapUser(Optional<User> entity, int id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
}
