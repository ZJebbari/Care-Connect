package com.zj.careconnect.service;

import com.zj.careconnect.exception.InvalidIdException;
import com.zj.careconnect.model.Appointment;
import com.zj.careconnect.model.Doctor;
import com.zj.careconnect.model.Patient;
import com.zj.careconnect.repo.AppointmentRepo;
import com.zj.careconnect.repo.DoctorRepo;
import com.zj.careconnect.repo.PatientRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentsService {
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;
    private final PatientRepo patientRepo;

    public Appointment addAppointment(Appointment appointment, int patient_id, int doctor_id) {
        // Validate IDs
        if (patient_id <= 0) {
            throw new InvalidIdException("Invalid patient ID: " + patient_id + ". ID must be greater than 0.");
        }

        if (doctor_id <= 0) {
            throw new InvalidIdException("Invalid doctor ID: " + doctor_id + ". ID must be greater than 0.");
        }

        // Fetch Patient and Doctor
        Optional<Patient> patient = patientRepo.findById(patient_id);
        Optional<Doctor> doctor = doctorRepo.findById(doctor_id);

        // Unwrap entities
        appointment.setPatient(PatientService.unwrapPatient(patient, patient_id));
        appointment.setDoctor(DoctorService.unwrapDoctor(doctor, doctor_id));

        // Validate Doctor's Availability
        Doctor doctorEntity = appointment.getDoctor();
        DayOfWeek appointmentDay = appointment.getDate().getDayOfWeek();
        LocalTime appointmentTime = appointment.getTime();

        // Check if the requested time falls within the doctor's availability
        boolean isAvailable = doctorEntity.getAvailability().stream()
                .anyMatch(slot -> slot.getDayOfWeek().equals(appointmentDay) &&
                        !appointmentTime.isBefore(slot.getStartTime()) &&
                        !appointmentTime.isAfter(slot.getEndTime()));

        if (!isAvailable) {
            throw new IllegalArgumentException(
                    "The doctor is not available on " + appointmentDay + " at " + appointmentTime);
        }

        // Check for overlapping appointments
        boolean isSlotBooked = appointmentRepo.existsByDoctorAndDateAndTime(
                doctorEntity, appointment.getDate(), appointment.getTime());
        if (isSlotBooked) {
            throw new IllegalArgumentException("The time slot is already booked.");
        }

        // Save the appointment
        return appointmentRepo.save(appointment);
    }
}
