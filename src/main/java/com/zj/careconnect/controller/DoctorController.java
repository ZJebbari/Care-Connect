package com.zj.careconnect.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.careconnect.DTO.DoctorRegistrationDto;
import com.zj.careconnect.model.Doctor;
import com.zj.careconnect.service.DoctorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("doctor")
@AllArgsConstructor
public class DoctorController {

    private DoctorService doctorService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerDoctor(
            @RequestPart("doctor") String doctorJson,
            @RequestPart("photo") MultipartFile photo) throws IOException {

        Doctor newDoctor = doctorService.addDoctor(doctorJson,photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDoctor);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateDoctor(@Valid @RequestBody DoctorRegistrationDto dto , @PathVariable int id) {
        return ResponseEntity.ok(doctorService.updateDoctor(dto,id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") int id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctorById(@PathVariable("id") int id) {
        doctorService.deleteDoctorById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getDoctors() {
        return ResponseEntity.ok(doctorService.getDoctors());
    }

    
}
