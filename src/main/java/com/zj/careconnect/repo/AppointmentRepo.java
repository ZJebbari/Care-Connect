package com.zj.careconnect.repo;


import com.zj.careconnect.model.Appointment;
import com.zj.careconnect.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    boolean existsByDoctorAndDateAndTime(Doctor doctor, LocalDate date, LocalTime time);
}
