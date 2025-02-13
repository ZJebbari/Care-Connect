package com.zj.careconnect.controller;

import com.zj.careconnect.DTO.PatientRegistrationDto;
import com.zj.careconnect.model.Patient;
import com.zj.careconnect.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@Valid @RequestBody PatientRegistrationDto dto) {
        Patient newPatient = patientService.registerPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") int id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") int id, @Valid @RequestBody PatientRegistrationDto dto) {
        return ResponseEntity.ok(patientService.updatePatient(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatientById(@PathVariable("id") int id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getPatients() {
        return ResponseEntity.ok(patientService.getPatients());
    }
}


