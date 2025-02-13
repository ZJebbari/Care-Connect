package com.zj.careconnect.controller;

import com.zj.careconnect.model.Appointment;
import com.zj.careconnect.service.AppointmentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("appointment")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentsService appointmentsService;

    @PostMapping("/schedule/patient/{patient_id}/doctor/{doctor_id}")
    public ResponseEntity<?> scheduleAppointment(@RequestBody Appointment appointment,
                                                 @PathVariable int patient_id,
                                                 @PathVariable int doctor_id) {
        try {
            // Call the service to schedule an appointment
            Appointment createdAppointment = appointmentsService.addAppointment(appointment, patient_id, doctor_id);
            return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Return bad request for validation errors
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Return internal server error for unexpected issues
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

